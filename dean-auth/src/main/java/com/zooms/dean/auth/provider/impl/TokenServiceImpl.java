package com.zooms.dean.auth.provider.impl;

import com.google.common.base.Joiner;
import com.zooms.dean.auth.common.AuthToken;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.ValidatorUtil;
import com.zooms.dean.auth.common.crypto.password.PasswordEncoder;
import com.zooms.dean.auth.domain.*;
import com.zooms.dean.auth.exceptions.InternalValidateException;
import com.zooms.dean.auth.exceptions.NotFoundResourceException;
import com.zooms.dean.auth.exceptions.RequestValidateException;
import com.zooms.dean.auth.mapper.UserMapper;
import com.zooms.dean.auth.module.UserBean;
import com.zooms.dean.auth.provider.TokenService;
import com.zooms.dean.auth.provider.UserManager;
import com.zooms.dean.auth.provider.token.JwtToken;
import com.zooms.dean.auth.provider.token.JwtTokenFactory;
import com.zooms.dean.auth.provider.token.UserPrincipal;
import com.zooms.dean.auth.service.*;
import com.zooms.dean.common.exceptions.NotFoundThirdAccountException;
import com.zooms.dean.common.web.View;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zooms.dean.auth.provider.token.JwtTokenFactory.*;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserMapper userMapper;

    private static final Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String PARAMS_PLATFORM = "platform";
    private static final String PARAMS_OPENID = "openid";
    private static final String PARAMS_USERNAME = "username";
    private static final String PARAMS_PASSWORD = "password";
    private static final String PARAMS_CODE = "code";
    private static final String PARAMS_TOKEN = "token";
    private static final String PARAMS_DEVICE_ID = "deviceId";

    private final RequestBindThirdLoginTokenValidate requestBindThirdLoginTokenValidate;
    private final RequestThirdLoginTokenValidate requestThirdLoginTokenValidate;
    private final RequestAccessTokenValidate requestAccessTokenValidate;
    private final CheckTokenValidate checkTokenValidate;
    private final RefreshTokenValidate refreshTokenValidate;
    private final JwtTokenFactory jwtTokenFactory;
    private final UserPrincipalBuilder userPrincipalBuilder;

    @Autowired
    public TokenServiceImpl(final PasswordEncoder passwordEncoder, final UserManager userManager,
                            final UserService userService, final UserRoleService userRoleService,
                            final JwtTokenFactory jwtTokenFactory,
                            final AuthorityService authorityService, final UserDealerService userDealerService) {

        this.jwtTokenFactory = jwtTokenFactory;
        this.userPrincipalBuilder = new UserPrincipalBuilder(authorityService, userRoleService);
        this.requestThirdLoginTokenValidate = new RequestThirdLoginTokenValidate(userService);
        this.requestBindThirdLoginTokenValidate = new RequestBindThirdLoginTokenValidate(userManager, userService);
        this.requestAccessTokenValidate = new RequestAccessTokenValidate(passwordEncoder, userService);
        this.checkTokenValidate = new CheckTokenValidate(jwtTokenFactory, authorityService, userDealerService);
        this.refreshTokenValidate = new RefreshTokenValidate(jwtTokenFactory);
    }

    @Override
    public View<AuthToken> requestAccessToken(Map<String, String> params) {

        requestAccessTokenValidate.invoke(params);

        User storageUser = requestAccessTokenValidate.storageUser;

        Map<String, Object> jwtMap = new HashMap<>();
        UserPrincipal userPrincipal = userPrincipalBuilder.invoke(storageUser);

        JwtToken accessToken = jwtTokenFactory.createAccessJwtToken(userPrincipal, jwtMap);
        JwtToken refreshToken = jwtTokenFactory.createRefreshToken(userPrincipal, jwtMap);
        return View.ofOk(new AuthToken(accessToken.getToken(), refreshToken.getToken(), userPrincipal.getRoles(), userPrincipal.getAuthorities()));
    }

    @Override
    public View<TokenPrincipal> requestCheckToken(Map<String, String> params) {

        try {

            checkTokenValidate.invoke(params);

            return View.ofOk("token is ok",
                    new TokenPrincipal(checkTokenValidate.userId, checkTokenValidate.username, checkTokenValidate.mobile, checkTokenValidate.roles,
                            checkTokenValidate.authorities, checkTokenValidate.dealerIds, checkTokenValidate.tableAuth));
        } catch (JwtException e) {

            LOG.warn(e.getMessage(), e);
            return View.of401Error("token is Invalid");
        }

    }

    @Override
    public View<AuthToken> requestRefreshToken(Map<String, String> params) {

        try {

            refreshTokenValidate.invoke(params);

            String rawToken = refreshTokenValidate.getToken();

            JwtToken accessToken = jwtTokenFactory.createAccessJwtToken(rawToken);
            JwtToken refreshToken = jwtTokenFactory.createRefreshToken(rawToken);

            return View
                    .ofOk("create refresh token is ok", new AuthToken(accessToken.getToken(), refreshToken.getToken()));
        } catch (JwtException e) {

            LOG.warn(e.getMessage(), e);
            return View.of401Error("token is Invalid");
        }
    }

    @Override
    public View<AuthToken> requestThirdLoginToken(Map<String, String> params) {

        requestThirdLoginTokenValidate.invoke(params);

        User storageUser = requestThirdLoginTokenValidate.storageUser;

        Map<String, Object> jwtMap = new HashMap<>();
        UserPrincipal userPrincipal = userPrincipalBuilder.invoke(storageUser);

        JwtToken accessToken = jwtTokenFactory.createAccessJwtToken(userPrincipal, jwtMap);
        JwtToken refreshToken = jwtTokenFactory.createRefreshToken(userPrincipal, jwtMap);

        return View.ofOk(new AuthToken(accessToken.getToken(), refreshToken.getToken(), userPrincipal.getRoles(), userPrincipal.getAuthorities()));
    }

    @Override
    public View<AuthToken> requestBindThirdLoginToken(Map<String, String> params) {

        requestBindThirdLoginTokenValidate.invoke(params);

        User storageUser = requestBindThirdLoginTokenValidate.storageUser;

        Map<String, Object> jwtMap = new HashMap<>();
        UserPrincipal userPrincipal = userPrincipalBuilder.invoke(storageUser);

        JwtToken accessToken = jwtTokenFactory.createAccessJwtToken(userPrincipal, jwtMap);
        JwtToken refreshToken = jwtTokenFactory.createRefreshToken(userPrincipal, jwtMap);

        return View.ofOk(new AuthToken(accessToken.getToken(), refreshToken.getToken(), userPrincipal.getRoles(), userPrincipal.getAuthorities()));
    }


    private static class UserPrincipalBuilder {

        private Collection<String> roles = new ArrayList<>();
        private Collection<String> authorities = new ArrayList<>();
        final UserRoleService userRoleService;


        final AuthorityService authorityService;

        UserPrincipalBuilder(AuthorityService authorityService, UserRoleService userRoleService) {
            this.userRoleService = userRoleService;

            this.authorityService = authorityService;
        }

        UserPrincipal invoke(User storageUser) {

            return new UserPrincipal(storageUser.getId(), storageUser.getUsername(), storageUser.getMobile(), roles, authorities);
        }

    }

    private static class RefreshTokenValidate implements Validate {

        private final JwtTokenFactory jwtTokenFactory;
        private String token;

        RefreshTokenValidate(final JwtTokenFactory jwtTokenFactory) {
            this.jwtTokenFactory = jwtTokenFactory;
        }

        @Override
        public void invoke(Map<String, String> params) {

            token = params.get(PARAMS_TOKEN);
            if (StringUtils.isEmpty(token)) {
                throw new RequestValidateException(PARAMS_TOKEN + " is not exist");
            }

            jwtTokenFactory.parseToken(token);

        }

        public String getToken() {
            return token;
        }
    }

    private static class CheckTokenValidate implements Validate {

        private final JwtTokenFactory jwtTokenFactory;
        private final AuthorityService authorityService;
        private final UserDealerService userDealerService;
        private String roles;
        private String authorities;
        private String username;
        private String mobile;
        private Long userId;
        private String dealerIds;
        private Map<String, String> tableAuth;

        CheckTokenValidate(final JwtTokenFactory jwtTokenFactory, final AuthorityService authorityService,
                           final UserDealerService userDealerService) {
            this.jwtTokenFactory = jwtTokenFactory;
            this.authorityService = authorityService;
            this.userDealerService = userDealerService;
        }

        @Override
        public void invoke(Map<String, String> params) {

            String token = params.get(PARAMS_TOKEN);
            if (StringUtils.isEmpty(token)) {
                throw new RequestValidateException(PARAMS_TOKEN + " is not exist");
            }

            Claims claims = jwtTokenFactory.parseToken(token);

            userId = Long.valueOf(claims.get(TOKEN_PARAMS_USER_ID).toString());
            mobile = (String) claims.get(TOKEN_PARAMS_MOBILE);
            roles = (String) claims.get(TOKEN_PARAMS_ROLES);
            authorities = (String) claims.get(TOKEN_PARAMS_AUTHORITIES);
            username = claims.getSubject();
            tableAuth = new HashMap<>();

            if (!StringUtils.isEmpty(authorities)) {
                String[] auth = authorities.split(TOKEN_PARAMS_SPLIT);
                List<Authority> authoritiesGlobal = authorityService.findByAuthorityIdsOrGlobal(
                        Arrays.asList(auth).parallelStream().map(Long::valueOf).collect(Collectors.toList()));
                for (Authority authority : authoritiesGlobal) {
                    if (tableAuth.containsKey(authority.getTableName())) {
                        String domainForce = tableAuth.get(authority.getTableName());
                        tableAuth.put(authority.getTableName(), domainForce + " and " + authority.getDomainForce());
                    } else {
                        tableAuth.put(authority.getTableName(), authority.getDomainForce());
                    }
                }
            }

            List<UserDealer> userDealerList = userDealerService.findByUserId(userId);
            if (!userDealerList.isEmpty()) {
                List<Long> dealerIdList = userDealerList.parallelStream().map(UserDealer::getDealerId).collect(Collectors.toList());
                dealerIds = Joiner.on(TOKEN_PARAMS_SPLIT).join(dealerIdList);
            }

        }
    }

    private static class RequestAccessTokenValidate implements Validate {

        private final PasswordEncoder passwordEncoder;
        private final UserService userStore;
        private User storageUser;

        RequestAccessTokenValidate(PasswordEncoder passwordEncoder, UserService userStore) {
            this.passwordEncoder = passwordEncoder;
            this.userStore = userStore;
        }

        @Override
        public void invoke(Map<String, String> params) {

            String username = params.get(PARAMS_USERNAME);
            String password = params.get(PARAMS_PASSWORD);
            String code = params.get(PARAMS_CODE);

            if (StringUtils.isEmpty(username)) {
                throw new RequestValidateException("用户名不存在");
            }

            /*boolean isMobile = ValidatorUtil.isMobile(username);
            boolean isEmail = ValidatorUtil.isEmail(username);
            boolean isUsername = ValidatorUtil.isUsername(username);

            if (!isMobile && !isEmail && !isUsername) {
                throw new RequestValidateException("用户名格式不正确");
            }*/

            storageUser = userStore.findByUsernameOrMobile(username);
            if (storageUser == null) {
                throw new NotFoundResourceException(username + " 不存在");
            }

            if (StringUtils.isEmpty(password)) {
                throw new RequestValidateException("密码不能为空");
            } else if (!StringUtils.isEmpty(code)) {
                if (!ValidatorUtil.isVerificationCode(code)) {
                    throw new RequestValidateException("验证码格式不正确");
                }
            } else {
                if (!passwordEncoder.matches(password, storageUser.getPassword())) {
                    throw new InternalValidateException("用户名和密码不匹配");
                }
            }
            // 更新设备ID用于推送消息
            String deviceId = params.get(PARAMS_DEVICE_ID);
            storageUser.setDeviceId(deviceId);
            userStore.save(storageUser);
        }

    }

    private static class RequestThirdLoginTokenValidate implements Validate {

        private final UserService userStore;
        private User storageUser;

        RequestThirdLoginTokenValidate(UserService userStore) {
            this.userStore = userStore;
        }

        @Override
        public void invoke(Map<String, String> params) {

            String platform = params.get(PARAMS_PLATFORM);

            if (StringUtils.isEmpty(platform)) {
                throw new RequestValidateException("请选择第三方登录平台");
            }

            String openid = params.get(PARAMS_OPENID);
            if ("qq".equals(platform)) {
                storageUser = userStore.findByQqOpenid(openid);
            } else if ("wechat".equals(platform)) {
                storageUser = userStore.findByWechatOpenid(openid);
            } else {
                throw new RequestValidateException("目前只支持QQ或微信授权登录");
            }

            if (storageUser == null) {
                throw new NotFoundThirdAccountException("用户未绑定过平台账号");
            }

        }

    }

    private static class RequestBindThirdLoginTokenValidate implements Validate {

        private final UserManager userManager;
        private final UserService userStore;
        private User storageUser;

        RequestBindThirdLoginTokenValidate(UserManager userManager, UserService userStore) {
            this.userStore = userStore;
            this.userManager = userManager;
        }

        @Override
        public void invoke(Map<String, String> params) {

            String mobile = params.get(PARAMS_USERNAME);

            if (StringUtils.isEmpty(mobile)) {
                throw new RequestValidateException("手机号不能为空");
            }

            userManager.checkVerificationCode(params);

            UserBean dealer = userStore.getUserBeanByMobile(mobile);

            if (dealer == null || StringUtils.isEmpty(dealer.getMobile())) {
                throw new NotFoundResourceException("手机号不存在");
            }

            storageUser = userStore.findByUsername(dealer.getUserName());

            if (storageUser == null) {
                throw new NotFoundResourceException("用户不存在或未同步到新平台");
            }

            String platform = params.get(PARAMS_PLATFORM);

            if (StringUtils.isEmpty(platform)) {
                throw new RequestValidateException("请选择第三方登录平台");
            }

            String openid = params.get(PARAMS_OPENID);
            if ("qq".equals(platform)) {
                storageUser.setQqOpenid(openid);
                userStore.save(storageUser);
            } else if ("wechat".equals(platform)) {
                storageUser.setWechatOpenid(openid);
                userStore.save(storageUser);
            } else {
                throw new RequestValidateException("目前只支持QQ或微信授权绑定");
            }

        }

    }
}
