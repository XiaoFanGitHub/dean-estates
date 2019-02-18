package com.zooms.dean.auth.provider.impl;

import com.zooms.dean.auth.common.BusinessUtils;
import com.zooms.dean.auth.common.DeanUtils;
import com.zooms.dean.auth.common.ValidatorUtil;
import com.zooms.dean.auth.common.bean.*;
import com.zooms.dean.auth.common.crypto.password.PasswordEncoder;
import com.zooms.dean.auth.config.DeanAuthSendSettings;
import com.zooms.dean.auth.domain.Role;
import com.zooms.dean.auth.domain.User;
import com.zooms.dean.auth.domain.UserDealer;
import com.zooms.dean.auth.domain.UserRoleKey;
import com.zooms.dean.auth.exceptions.NotFoundResourceException;
import com.zooms.dean.auth.exceptions.RequestValidateException;
import com.zooms.dean.auth.provider.SendMessageLimit;
import com.zooms.dean.auth.provider.UserManager;
import com.zooms.dean.auth.provider.VerificationCodeStore;
import com.zooms.dean.auth.provider.aliyun.SmsMessageSender;
import com.zooms.dean.auth.provider.email.EmailMessageSender;
import com.zooms.dean.auth.service.*;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zooms.dean.auth.provider.MessageConstant.PARAMS_SUBJECT;
import static com.zooms.dean.auth.provider.MessageConstant.PARAMS_SUBJECT_SEND;

/**
 * @author slacrey
 * @since 2018/1/17
 */
@Service
public class UserManagerImpl implements UserManager {

    private static final Logger LOG = LoggerFactory.getLogger(UserManagerImpl.class);

    private static final String PARAMS_SEND_TYPE = "send_type";
    private static final String PARAMS_CODE = "code";
    private static final String PARAMS_USERNAME = "username";
    private static final String PARAMS_PASSWORD = "password";
    private static final String PARAMS_ID = "id";

    @Value("${init.data.role}")
    private String roleCode;

    private final SendMessageLimit sendMessageLimit;
    private final VerificationCodeStore verificationCodeStore;
    private final SmsMessageSender smsMessageSender;
    private final EmailMessageSender emailMessageSender;
    private final DeanAuthSendSettings deanAuthSendSettings;
    private final VerificationCodeValidate verificationCodeValidate;
    private final RegisterValidate registerValidate;
    private final CheckCodeValidate checkCodeValidate;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserDealerService userDealerService;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserManagerImpl(SendMessageLimit sendMessageLimit, VerificationCodeStore verificationCodeStore,
                           SmsMessageSender smsMessageSender, EmailMessageSender emailMessageSender,
                           DeanAuthSendSettings deanAuthSendSettings, UserService userService, UserDealerService userDealerService,
                           UserRoleService userRoleService, PasswordEncoder passwordEncoder) {

        this.sendMessageLimit = sendMessageLimit;
        this.verificationCodeStore = verificationCodeStore;
        this.smsMessageSender = smsMessageSender;
        this.emailMessageSender = emailMessageSender;
        this.deanAuthSendSettings = deanAuthSendSettings;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userDealerService = userDealerService;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeValidate = new VerificationCodeValidate();
        this.registerValidate = new RegisterValidate();
        this.checkCodeValidate = new CheckCodeValidate();
    }

    @Override
    public View<Map<String, Object>> requestUserRegister(Map<String, String> params) {

        registerValidate.invoke(params);

        UserBuilder userBuilder = new UserBuilder(params);

        String username = userBuilder.username;

        UsernameType type = registerValidate.usernameType;

        String phone = params.get("username");

        if (UsernameType.PHONE.equals(type)) {
            userBuilder.setMobile(phone);
            userBuilder.setPhone(phone);
        }

        handlerVerificationCodeCheck(params, phone, type);

        User storageUser = userService.findByUsername(username);

        User user = userBuilder.getUser();
        if (storageUser != null) {
            user.setId(storageUser.getId());
        }
        userService.save(user);


        Map<String, Object> data = new HashMap<>();
        data.put(PARAMS_ID, user.getId());
        data.put(PARAMS_USERNAME, user.getUsername() == null ? "" : user.getUsername());

        return View.ofOk("用户注册成功", data);
    }

    @Override
    public View<UserItem> requestUserSave(UserItem params) {

        User destUser = new User();
        BeanUtils.copyProperties(params, destUser);
        destUser.setPhone(destUser.getMobile());

        if (!StringUtils.isEmpty(destUser.getPassword())) {
            destUser.setPassword(passwordEncoder.encode(destUser.getPassword()));
        }
        userService.save(destUser);
        params.setId(destUser.getId());
        return View.ofOk(params);

    }

    @Override
    public View<String> requestUserDelete(Long userId) {
        userService.deleteByUserId(userId);
        return View.ofOk();
    }

    @Override
    public View<String> requestVerificationCode(Map<String, String> params) {

        View<String> result = View.ofError("发送验证码失败");
        
        //验证
        LOG.info("开始进行参数校验");
        verificationCodeValidate.invoke(params);

        LOG.info("参数校验完成");

        String username = verificationCodeValidate.username;
        SendType sendType = verificationCodeValidate.sendType;
        String code;
        String template = deanAuthSendSettings.getMail().getTemplate().getTemplateRegister();
        Map<String, Object> sendParams = new HashMap<>();
        UsernameType type = verificationCodeValidate.usernameType;
        switch (type) {
            case EMAIL:

                LOG.info("开始进行发送频率等限制");
                sendMessageLimit.filter(username);
                LOG.info("通过频率次数限制");
                code = DeanUtils.randomInt(6);
                verificationCodeStore.storeVerificationCode(username, code);

                sendParams.put(PARAMS_CODE, code);
                sendParams.put(PARAMS_SUBJECT, PARAMS_SUBJECT_SEND);

                if (sendType.equals(SendType.register)) {
                    template = deanAuthSendSettings.getMail().getTemplate().getTemplateRegister();
                } else if (sendType.equals(SendType.login)) {
                    template = deanAuthSendSettings.getMail().getTemplate().getTemplateLogin();
                } else if (sendType.equals(SendType.common)) {
                    template = deanAuthSendSettings.getMail().getTemplate().getTemplateCommon();
                }
                LOG.info("开始发送短信");
                result = emailMessageSender.sendMessage(username, sendParams, template);
                LOG.info("发送短信完成");
                break;
            case PHONE:

                LOG.info("开始进行发送频率等限制");
                sendMessageLimit.filter(username);
                LOG.info("通过频率次数限制");
                code = DeanUtils.randomInt(6);
                verificationCodeStore.storeVerificationCode(username, code);

                sendParams.put(PARAMS_CODE, code);

                if (sendType.equals(SendType.register)) {
                    template = deanAuthSendSettings.getSms().getTemplate().getTemplateRegister();
                } else if (sendType.equals(SendType.login)) {
                    template = deanAuthSendSettings.getSms().getTemplate().getTemplateLogin();
                } else if (sendType.equals(SendType.modify)) {
                    template = deanAuthSendSettings.getSms().getTemplate().getTemplateLogin();
                } else if (sendType.equals(SendType.common)) {
                    template = deanAuthSendSettings.getSms().getTemplate().getTemplateCommon();
                }

                LOG.info("开始发送短信");
                result = smsMessageSender.sendMessage(username, sendParams, template);
                LOG.info("发送短信完成");
                break;
            default:
                break;
        }

        return result;

    }

    @Override
    public View<String> checkVerificationCode(Map<String, String> params) {

        checkCodeValidate.invoke(params);

        String username = params.get(PARAMS_USERNAME);

        UsernameType type = checkCodeValidate.usernameType;
        handlerVerificationCodeCheck(params, username, type);
        return View.ofOk("验证码正确");
    }

    private void handlerVerificationCodeCheck(Map<String, String> params, String username, UsernameType type) {
        String code;
        Set<String> codeSet;
        switch (type) {
            case EMAIL:
                code = params.get(PARAMS_CODE);
                codeSet = verificationCodeStore.findVerificationCode(username);
                if (codeSet == null || !codeSet.contains(code)) {
                    throw new NotFoundResourceException("验证码验证错误");
                }
                break;

            case PHONE:
                code = params.get(PARAMS_CODE);
                codeSet = verificationCodeStore.findVerificationCode(username);
                if (codeSet == null || !codeSet.contains(code)) {
                    throw new NotFoundResourceException("验证码验证错误");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View<String> registerUserRoleBind(UserRoleItem params) {

        if (params.getUserId() == null) {
            throw new RequestValidateException("请求参数：userId is null");
        }
        if (params.getRoleId() == null) {
            throw new RequestValidateException("请求参数：roleId is null");
        }
        User user = userService.findByUserId(params.getUserId());
        if (user == null) {
            return View.ofError("传入的用户不存在");
        }
        // 删除用户对应的角色
        userRoleService.deleteByUserId(params.getUserId());
        //保存用户和角色关系
        userRoleService.saveUserRole(params.getUserId(), params.getRoleId());

        return View.ofOk("绑定角色到用户成功");
    }


    @Override
    public View<Long> findUserRoles(UserRoleItem params) {
        List<UserRoleKey> userRoleKeys = userRoleService.findByUserId(params.getUserId());
        if (!userRoleKeys.isEmpty()) {
            List<Long> roleIds = userRoleKeys.parallelStream().map(UserRoleKey::getRoleId).collect(Collectors.toList());
            if (!roleIds.isEmpty()) {
                return View.ofOk(roleIds.get(0));
            }
        }
        return View.ofOk();
    }

    @Override
    public View<Page<User>> findUserPage(Query<UserItem> query) {

        Query<User> userQuery = new Query<>();
        userQuery.setPage(query.getPage());
        userQuery.setSize(query.getSize());
        userQuery.setData(BusinessUtils.toUser(query.getData()));

        Page<User> userPage = userService.findUserPage(userQuery);
        return View.ofOk(userPage);
    }

    private static class VerificationCodeValidate implements Validate {

        private static final Logger LOG = LoggerFactory.getLogger(VerificationCodeValidate.class);
        private UsernameType usernameType = UsernameType.USERNAME;
        private SendType sendType = SendType.register;
        private String username;

        @Override
        public void invoke(Map<String, String> params) throws RequestValidateException {
            
            username = params.get(PARAMS_USERNAME);
            
            boolean isMobile = ValidatorUtil.isMobile(username);
            boolean isEmail = ValidatorUtil.isEmail(username);
            
            if (StringUtils.isEmpty(username)) {
                throw new RequestValidateException("手机号或者邮箱不能为空");
            }

            if (!isEmail && !isMobile) {
                throw new RequestValidateException("手机号或者邮箱格式不正确");
            } else if (isEmail) {
                usernameType = UsernameType.EMAIL;
            } else {
                usernameType = UsernameType.PHONE;
            }
            
            String send = params.get(PARAMS_SEND_TYPE);
            if (!StringUtils.isEmpty(send)) {
                try {
                    sendType = SendType.valueOf(send);
                } catch (IllegalArgumentException e) {
                    if (LOG.isInfoEnabled()) {
                        LOG.info(e.getMessage(), e);
                    }
                }
            }

        }
    }

    private static class RegisterValidate implements Validate {


        private UsernameType usernameType = UsernameType.USERNAME;


        @Override
        public void invoke(Map<String, String> params) throws RequestValidateException {

            if (!params.containsKey(PARAMS_PASSWORD)) {
                throw new RequestValidateException("密码不正确");
            }

            if (!params.containsKey(PARAMS_USERNAME)) {
                throw new RequestValidateException("用户名不存在");
            }

            String username = params.get(PARAMS_USERNAME);
            boolean isMobile = ValidatorUtil.isMobile(username);
            boolean isMail = ValidatorUtil.isEmail(username);
            boolean isUsername = ValidatorUtil.isUsername(username) || ValidatorUtil.isContainChinese(username);

            if (!isMobile && !isMail && !isUsername) {
                throw new RequestValidateException("用户名格式不正确，它应该是一个手机号码或者邮箱地址");
            }

            if ((isMobile || isMail) && !params.containsKey(PARAMS_CODE)) {
                throw new RequestValidateException("验证码不存在");
            }

            if (isMail) {
                usernameType = UsernameType.EMAIL;
            }
            if (isMobile) {
                usernameType = UsernameType.PHONE;
            }
            if (isUsername) {
                usernameType = UsernameType.USERNAME;
            }
        }
    }

    private static class CheckCodeValidate implements Validate {


        private UsernameType usernameType = UsernameType.USERNAME;


        @Override
        public void invoke(Map<String, String> params) throws RequestValidateException {

            String username = params.get(PARAMS_USERNAME);
            boolean isMobile = ValidatorUtil.isMobile(username);
            boolean isMail = ValidatorUtil.isEmail(username);

            if (!isMobile && !isMail) {
                throw new RequestValidateException("用户名格式不正确，它应该是一个手机号码或者邮箱地址");
            }


            if (!params.containsKey(PARAMS_CODE)) {
                throw new RequestValidateException("验证码不存在");
            }
            String code = params.get(PARAMS_CODE);
            if (StringUtils.isEmpty(code)) {
                throw new RequestValidateException("验证码不存在");
            }

            if (isMail) {
                usernameType = UsernameType.EMAIL;
            }
            if (isMobile) {
                usernameType = UsernameType.PHONE;
            }
        }
    }

    private class UserBuilder {

        private String username;
        private String password;
        private String mobile;
        private String phone;

        UserBuilder(Map<String, String> params) {
            password = passwordEncoder.encode(params.get(PARAMS_PASSWORD));
            username = params.get(PARAMS_USERNAME);
        }

        User getUser() {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setPhone(phone);
            user.setMobile(mobile);
            return user;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
