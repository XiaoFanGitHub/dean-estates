package com.zooms.dean.auth.provider;


import com.zooms.dean.auth.common.bean.UserItem;
import com.zooms.dean.auth.common.bean.UserRoleItem;
import com.zooms.dean.auth.domain.User;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;

import java.util.List;
import java.util.Map;

/**
 * @author slacrey
 * @since 2018/1/17
 */
public interface UserManager {

    /**
     * 用户注册
     *
     * @param params map key:value
     *               {username:"",password:""}
     * @return 返回注册成功或失败的信息
     */
    View<Map<String, Object>> requestUserRegister(Map<String, String> params);

    /**
     * 用户保存
     *
     * @param params map key:value
     *               {username:"",password:""}
     * @return 返回注册成功或失败的信息
     */
    View<UserItem> requestUserSave(UserItem params);

    /**
     * 用户删除
     *
     * @param userId 用户编号
     * @return 返回注册成功或失败的信息
     */
    View<String> requestUserDelete(Long userId);

    /**
     * 发送验证码
     *
     * @param params map
     *               {
     *               username:"",
     *               send_type:"login/register/modify"
     *               }
     * @return 发送验证码成功或失败提示
     */
    View<String> requestVerificationCode(Map<String, String> params);

    /**
     * 发送验证码
     *
     * @param params map
     *               {
     *               username:"",
     *               code:"验证码"
     *               }
     * @return 发送验证码成功或失败提示
     */
    View<String> checkVerificationCode(Map<String, String> params);


    /**
     * 用户角色绑定
     *
     * @param params 用户角色对象
     * @return 用户角色绑定成功或失败提示
     */
    View<String> registerUserRoleBind(UserRoleItem params);




    /**
     * 用户角色绑定
     *
     * @param params 用户角色对象
     * @return 用户角色绑定成功或失败提示
     */
    View<Long> findUserRoles(UserRoleItem params);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    View<Page<User>> findUserPage(Query<UserItem> query);

}
