package com.zooms.dean.auth.controller.api;

import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.common.bean.UserItem;
import com.zooms.dean.auth.common.bean.UserRoleItem;
import com.zooms.dean.auth.domain.User;
import com.zooms.dean.auth.exceptions.RequestValidateException;
import com.zooms.dean.auth.provider.UserManager;
import com.zooms.dean.auth.service.UserService;
import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.tool.AliyunOssTool;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户对外接口
 *
 * @author linfeng
 */
@RestController
@RequestMapping("/{v}/user")
public class UserController {

    @Autowired
    private UserManager userManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    AliyunOssTool aliyunOssTool;
    
    @Autowired
    private TokenPrincipalProvider tokenPrincipalProvider;


    /**
     * 用户注册接口
     *
     * @param params 用注册参数
     * @return 返回注册消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/register",
            method = {RequestMethod.POST}
    )
    public View<Map<String, Object>> registerUserEndpoint(@RequestBody Map<String, String> params) throws RequestValidateException {
        return userManager.requestUserRegister(params);
    }

    /**
     * 用户保存
     *
     * @param user 用注册参数
     * @return 返回注册消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/save",
            method = {RequestMethod.POST}
    )
    public View<UserItem> saveUserEndpoint(@RequestBody UserItem user) throws RequestValidateException {

        return userManager.requestUserSave(user);
    }

    /**
     * 用户角色绑定
     *
     * @param params 角色绑定对象
     * @return 返回绑定消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/role/bind",
            method = {RequestMethod.POST}
    )
    public View<String> registerUserRoleBindEndpoint(@Validated @RequestBody UserRoleItem params) throws RequestValidateException {

        return userManager.registerUserRoleBind(params);
    }

    /**
     * 分页查询用户
     *
     * @param query 分页参数
     * @return 查询结果
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/list",
            method = RequestMethod.POST
    )
    public View<Page<User>> requestPageRole(@Validated @RequestBody Query<UserItem> query) {
        return userManager.findUserPage(query);
    }

    /**
     * 用户角色绑定
     *
     * @param params 角色绑定对象
     * @return 返回绑定消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/role/user",
            method = {RequestMethod.POST}
    )
    public View<Long> getUserRoleBindEndpoint(@RequestBody UserRoleItem params) throws RequestValidateException {

        return userManager.findUserRoles(params);
    }

    /**
     * 用户删除
     *
     * @param userId 用户编号
     * @return 返回注册消息
     * @throws RequestValidateException 请求验证异常
     */
    @ApiVersion(1)
    @RequestMapping(
            value = "/delete/{userId}",
            method = {RequestMethod.POST}
    )
    public View<String> deleteUserEndpoint(@PathVariable Long userId) throws RequestValidateException {

        return userManager.requestUserDelete(userId);
    }




    /**
     * 登录用户信息
     * @param request
     * @param response
     * @param
     * @return
     */
    @ApiVersion(1)
    @RequestMapping("/info")
    public View<Map<String, Object>> info(HttpServletRequest request, HttpServletResponse response) {
        View<Map<String, Object>> view = new View<Map<String, Object>>(20000, "获取数据成功");
        Map<String, Object> userMap = new HashMap<String, Object>();
        Long userId = tokenPrincipalProvider.getTokenPrincipal().getUserId();
        User user = userService.findByUserId(userId);
        userMap.put("userName", user.getUsername());
        userMap.put("avatar", aliyunOssTool.getImgUrl(user.getAvatar()));
        view.setData(userMap);
        return view;
    }

}
