package com.zooms.dean.auth.service.impl;

import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.domain.User;
import com.zooms.dean.auth.domain.UserExample;
import com.zooms.dean.auth.mapper.UserMapper;
import com.zooms.dean.auth.module.UserBean;
import com.zooms.dean.auth.service.UserService;
import com.zooms.dean.common.tool.BeanUtils;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import com.zooms.dean.common.web.View;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author linfeng
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenPrincipalProvider tokenPrincipalProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User record) {
        User oldRecord = userMapper.selectByPrimaryKey(record.getId());
        if (oldRecord == null) {
            userMapper.insertSelective(record);
        } else {
            record.setDelFlag(User.DEL_FLAG_NORMAL);
            userMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public User findByUserId(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public User findByUsernameOrMobile(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or().andMobileEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public User findByMobile(String mobile) {
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long id) {
        User user = new User();
        user.setId(id);
        user.setDelFlag(User.DEL_FLAG_DELETE);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setDelFlag(User.DEL_FLAG_DELETE);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Page<User> findUserPage(Query<User> query) {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        UserExample.Criteria criteria = example.createCriteria();
        User user = query.getData();
        if (StringUtils.isNotBlank(user.getUsername())) {
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            criteria.andPhoneEqualTo(user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getName())) {
            criteria.andNameLike("'" + user.getName() + "%'");
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            criteria.andEmailEqualTo(user.getEmail());
        }
        List<User> resultList = userMapper.selectByExampleWithRowbounds(example, new RowBounds(query.getOffset(), query.getLimit()));
        long total = userMapper.countByExample(example);

        Page<User> resultPage = Page.of(query);
        resultPage.setTotalElements(total);
        for (User user1: resultList) {
            user1.setPassword(null);
        }
        resultPage.setContent(resultList);

        return resultPage;
    }

    @Override
    public User findByQqOpenid(String openid) {
        UserExample example = new UserExample();
        example.createCriteria().andQqOpenidEqualTo(openid);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public User findByWechatOpenid(String openid) {
        UserExample example = new UserExample();
        example.createCriteria().andWechatOpenidEqualTo(openid);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
    }
    
    /**
     * 根据手机号查询经销商用户信息
     *
     * @param mobile 用户名
     * @return 用户基本信息{@link UserBean}
     */
    @Override
    public UserBean getUserBeanById(Integer userId) {
        UserBean userBean = null;
        @SuppressWarnings("unchecked")
        View<UserBean> result =
                restTemplate
                        .postForEntity("http://dean-platform/platform/v1/inner/user/info/" + userId, null, View.class)
                        .getBody();
        if (result.getCode() == 20000 && result.getData() != null) {
            userBean = new UserBean();
            BeanUtils.copy(result.getData(), userBean);
        }
        return userBean;
    }

    /**
     * 根据手机号查询经销商用户信息
     *
     * @param mobile 用户名
     * @return 用户基本信息{@link UserBean}
     */
    @Override
    public UserBean getUserBeanByMobile(String mobile) {
        UserBean userBean = null;
        @SuppressWarnings("unchecked")
        View<UserBean> result =
                restTemplate
                        .postForEntity("http://dean-platform/platform/v1/inner/user/search/" + mobile, null, View.class)
                        .getBody();
        if (result.getCode() == 20000 && result.getData() != null) {
            userBean = new UserBean();
            BeanUtils.copy(result.getData(), userBean);
        }
        return userBean;
    }

    /**
     * 查询当前接单员的sap账号
     * @return
     */
    @Override
    public String getSapAccount(Long id){

        return  userMapper.selectByPrimaryKey(id) == null ? null : userMapper.selectByPrimaryKey(id).getSapAccount();
    }
}
