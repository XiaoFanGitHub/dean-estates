package com.zooms.dean.auth.service;

import com.zooms.dean.auth.domain.User;
import com.zooms.dean.auth.module.UserBean;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;

public interface UserService {

    /**
     * 保存用户
     * @param record 用户
     */
    void save(User record);

    /**
     * 查询用户
     * @param id 用户主键
     * @return 用户
     */
    User findByUserId(Long id);

    /**
     * 查询用户
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 查询用户
     * @param username 用户名
     * @return 用户
     */
    User findByUsernameOrMobile(String username);

    /**
     * 查询用户
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);

    /**
     * 删除用户
     * @param id 主键
     */
    void deleteByUserId(Long id);

    /**
     * 删除用户
     * @param username 用户名
     */
    void deleteByUsername(String username);

    /**
     * 分页查询
     * @param query 查询参数
     * @return 分页
     */
    Page<User> findUserPage(Query<User> query);

    User findByQqOpenid(String openid);

    User findByWechatOpenid(String openid);
    
    /**
     * 经销商用户
     * @param mobile
     * @return
     */
    UserBean getUserBeanById(Integer userId);
    
    /**
     * 经销商用户
     * @param mobile
     * @return
     */
    UserBean getUserBeanByMobile(String mobile);

    /**
     * 查询当前接单员的sap账号
     * @return
     */
    String getSapAccount(Long id);

}
