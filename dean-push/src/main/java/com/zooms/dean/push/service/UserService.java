package com.zooms.dean.push.service;

import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.common.tool.BeanUtils;
import com.zooms.dean.common.web.View;
import com.zooms.dean.push.model.AuthUserBean;
import com.zooms.dean.push.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * 用户管理业务类
 * 
 * @author zhaoljin
 * @date 2018年5月9日
 */
@Component
public class UserService {

    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    private TokenPrincipalProvider tokenPrincipalProvider;
    
    /**
     * 获取当前登录用户ID
     * 
     * @return userId
     */
    public Integer getLoginUserId() {
        Integer userId = tokenPrincipalProvider.getTokenPrincipal().getUserId().intValue();
        return userId;
    }

    /**
     * 获取当前登录用户名
     * 
     * @return userId
     */
    public String getLoginUserName() {
        String userName = tokenPrincipalProvider.getTokenPrincipal().getUsername();
        return userName;
    }

    /**
     * 根据用户ID查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户基本信息{@link UserBean}
     */
    /**
     * 根据用户ID查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户基本信息{@link UserBean}
     */
    public UserBean getUserBeanById(Integer userId) {
        UserBean userBean = new UserBean();
        AuthUserBean authUserBean = getAuthUserById(userId);
        userBean = getUserBeanByName(authUserBean.getUsername());
        return userBean;
    }
    
    /**
     * 根据用户名查询用户信息
     * 
     * @param userName 用户名
     * @return 用户基本信息{@link UserBean}
     */
    public UserBean getUserBeanByName(String userName) {
        UserBean userBean = new UserBean();
        @SuppressWarnings("unchecked")
        View<LinkedHashMap<String, Object>> result =
            restTemplate.postForEntity("http://dean-platform/platform/v1/inner/user/get/" + userName, null, View.class)
                .getBody();
        if (result.getCode() == 20000 && result.getData() != null) {
            BeanUtils.copy(result.getData(), userBean);
        }
        return userBean;
    }
    
    /**
     * 根据用户ID查询授权用户信息
     * 
     * @param userId 用户ID
     * @return 用户基本信息{@link UserBean}
     */
    public AuthUserBean getAuthUserById(Integer userId) {
        AuthUserBean userBean = new AuthUserBean();
        if (userId == null) {
            return userBean;
        }
        @SuppressWarnings("unchecked")
        View<LinkedHashMap<String, Object>> result =
            restTemplate.postForEntity("http://dean-auth/auth/v1/inner/auth/user/info/" + userId, null, View.class)
                .getBody();
        if (result.getCode() == 20000 && result.getData() != null) {
            BeanUtils.copy(result.getData(), userBean);
        }
        return userBean;
    }

}
