package com.zooms.dean.auth.service;

import com.zooms.dean.auth.domain.Authority;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;

import java.util.List;

/**
 * @author linfeng
 */
public interface AuthorityService {

    /**
     * 查询权限对象
     *
     * @param authorityId 主键
     * @return
     */
    Authority findByAuthorityId(Long authorityId);

    /**
     * 获取所有全局权限或根据权限ID查询
     *
     * @param authorityIds 权限ID集合
     * @return 权限集合
     */
    List<Authority> findByAuthorityIdsOrGlobal(List<Long> authorityIds);

    /**
     * 获取所有全局权限
     *
     * @return 权限集合
     */
    List<Authority> findAllGlobal();

    /**
     * 保存权限
     *
     * @param record 权限对象{@link Authority}
     */
    void saveAuthority(Authority record);

    /**
     * 保存权限
     *
     * @param authorityId 权限编号
     */
    void deleteAuthority(Long authorityId);

    /**
     * 分页查询权限
     *
     * @param query 分页查询
     * @return 分页对象
     */
    Page<Authority> findPageAuthority(Query<Authority> query);

}
