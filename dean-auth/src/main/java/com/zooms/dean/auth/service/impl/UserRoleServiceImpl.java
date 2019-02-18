package com.zooms.dean.auth.service.impl;

import com.zooms.dean.auth.domain.UserRoleExample;
import com.zooms.dean.auth.domain.UserRoleKey;
import com.zooms.dean.auth.mapper.UserRoleMapper;
import com.zooms.dean.auth.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linfeng
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void saveUserRole(Long userId, Long roleId) {
        if(null != userId) {
            UserRoleKey userRoleKey = findByUserIdAndRoleId(userId, roleId);
            if (userRoleKey == null) {
                userRoleKey = new UserRoleKey();
                userRoleKey.setUserId(userId);
                userRoleKey.setRoleId(roleId);
                userRoleMapper.insertSelective(userRoleKey);
            }
        }
    }

    @Override
    public UserRoleKey findByUserIdAndRoleId(Long userId, Long roleId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);
        List<UserRoleKey> userRoleKeyList = userRoleMapper.selectByExample(example);
        if (userRoleKeyList == null || userRoleKeyList.isEmpty()) {
            return null;
        }
        return userRoleKeyList.get(0);
    }

    @Override
    public List<UserRoleKey> findByUserId(Long userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("user_id asc");
        return userRoleMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserIdAndRoleId(Long userId, Long roleId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRoleMapper.deleteByExample(example);
    }

}
