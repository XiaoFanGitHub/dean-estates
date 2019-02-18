package com.zooms.dean.auth.service;

import com.zooms.dean.auth.domain.UserRoleKey;

import java.util.List;

public interface UserRoleService {

    /**
     * 保存用户角色对象
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     */
    void saveUserRole(Long userId, Long roleId);

    /**
     * 根据用户编号和权限编号查询角色对象
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return 用户角色对象
     */
    UserRoleKey findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户编号查询用户对应的角色信息
     *
     * @param userId 用户编号
     * @return 用户权限集合
     */
    List<UserRoleKey> findByUserId(Long userId);

    /**
     * 根据用户编号和角色编号删除对应关系
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     */
    void deleteByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 删除用户角色关联
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);


}
