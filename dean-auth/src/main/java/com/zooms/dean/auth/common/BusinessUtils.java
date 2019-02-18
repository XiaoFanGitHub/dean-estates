package com.zooms.dean.auth.common;

import com.zooms.dean.auth.common.bean.*;
import com.zooms.dean.auth.domain.*;

public final class BusinessUtils {

    public static final String OPERA_ADD = "add";
    public static final String OPERA_REMOVE = "remove";

    public static User toUser(UserItem item) {
        User key = new User();
        key.setName(item.getName());
        key.setUsername(item.getUsername());
        key.setEmail(item.getEmail());
        return key;
    }

    public static UserRoleKey toUserRoleKey(Long roleId, Long userId) {
        UserRoleKey key = new UserRoleKey();
        key.setRoleId(roleId);
        key.setUserId(userId);
        key.setDelFlag(Role.DEL_FLAG_NORMAL);
        return key;
    }

    public static RoleMenuKey toRoleMenuKey(Long roleId, Long menuId) {
        RoleMenuKey key = new RoleMenuKey();
        key.setRoleId(roleId);
        key.setMenuId(menuId);
        key.setDelFlag(Role.DEL_FLAG_NORMAL);
        return key;
    }

    public static RoleAuthorityKey toRoleAuthorityKey(Long roleId, Long authorityId) {
        RoleAuthorityKey key = new RoleAuthorityKey();
        key.setRoleId(roleId);
        key.setAuthorityId(authorityId);
        key.setDelFlag(Role.DEL_FLAG_NORMAL);
        return key;
    }

    public static App toApp(AppItem appItem) {
        App role = new App();
        role.setAppKey(appItem.getAppKey());
        role.setAppName(appItem.getAppName());
        role.setNotUpdate(appItem.getNotUpdate());
        return role;
    }



    public static Authority toAuthority(AuthorityItem authorityItem) {
        Authority authority = new Authority();
        authority.setId(authorityItem.getId());
        authority.setName(authorityItem.getName());
        authority.setTableName(authorityItem.getTableName());
        authority.setGlobal(authorityItem.getGlobal());
        authority.setDomainForce(authorityItem.getDomainForce());
        return authority;
    }



}
