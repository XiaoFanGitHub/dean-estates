package com.zooms.dean.auth.service.impl;

import com.zooms.dean.auth.domain.UserDealer;
import com.zooms.dean.auth.domain.UserDealerExample;
import com.zooms.dean.auth.mapper.UserDealerMapper;
import com.zooms.dean.auth.service.UserDealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/6/13
 */
@Service
public class UserDealerServiceImpl implements UserDealerService {

    @Autowired
    private UserDealerMapper userDealerMapper;

    @Override
    public void saveUserDealer(Long userId, Long dealerId, String dealerName) {
        UserDealer userRoleKey = new UserDealer();
        userRoleKey.setUserId(userId);
        userRoleKey.setDealerId(dealerId);
        userRoleKey.setDealerName(dealerName);
        userDealerMapper.insertSelective(userRoleKey);
    }

    @Override
    public UserDealer findByUserIdAndDealerId(Long userId, Long dealerId) {
        UserDealerExample example = new UserDealerExample();
        example.createCriteria().andUserIdEqualTo(userId).andDealerIdEqualTo(dealerId);
        List<UserDealer> userRoleKeyList = userDealerMapper.selectByExample(example);
        if (userRoleKeyList == null || userRoleKeyList.isEmpty()) {
            return null;
        }
        return userRoleKeyList.get(0);
    }

    @Override
    public List<UserDealer> findByUserId(Long userId) {
        UserDealerExample example = new UserDealerExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userDealerMapper.selectByExample(example);
    }

    @Override
    public void deleteByUserIdAndDealerId(Long userId, Long dealerId) {
        UserDealerExample example = new UserDealerExample();
        example.createCriteria().andUserIdEqualTo(userId).andDealerIdEqualTo(dealerId);
        userDealerMapper.deleteByExample(example);
    }

    @Override
    public void deleteByUserId(Long userId) {
        UserDealerExample example = new UserDealerExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userDealerMapper.deleteByExample(example);
    }
}
