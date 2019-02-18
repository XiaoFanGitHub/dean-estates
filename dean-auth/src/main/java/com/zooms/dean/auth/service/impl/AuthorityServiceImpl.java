package com.zooms.dean.auth.service.impl;

import com.zooms.dean.auth.domain.Authority;
import com.zooms.dean.auth.domain.AuthorityExample;
import com.zooms.dean.auth.mapper.AuthorityMapper;
import com.zooms.dean.auth.service.AuthorityService;
import com.zooms.dean.common.tool.StringUtils;
import com.zooms.dean.common.web.Page;
import com.zooms.dean.common.web.Query;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linfeng
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    @Cacheable(value = "AuthorityService", key = "'findByAuthorityIdsOrGlobal_' + #authorityIds")
    public List<Authority> findByAuthorityIdsOrGlobal(List<Long> authorityIds) {
        AuthorityExample example = new AuthorityExample();
        AuthorityExample.Criteria criteriaGlobal = example.createCriteria();
        criteriaGlobal.andGlobalEqualTo(true);

        if (authorityIds == null || !authorityIds.isEmpty()) {
            AuthorityExample.Criteria criteriaAuthorityIds = example.or();
            criteriaAuthorityIds.andIdIn(authorityIds);
        }

        return authorityMapper.selectByExample(example);
    }

    @Override
    public Authority findByAuthorityId(Long authorityId) {
        return authorityMapper.selectByPrimaryKey(authorityId);
    }

    @Override
    @Cacheable(value = "AuthorityService")
    public List<Authority> findAllGlobal() {
        AuthorityExample example = new AuthorityExample();
        example.createCriteria().andGlobalEqualTo(true);
        return authorityMapper.selectByExample(example);
    }

    @Override
    @Cacheable(value = "AuthorityService")
    @Transactional(rollbackFor = Exception.class)
    public void saveAuthority(Authority record) {
        Authority oldAuthority = authorityMapper.selectByPrimaryKey(record.getId());
        if (oldAuthority == null) {
            authorityMapper.insertSelective(record);
        } else {
            authorityMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "AuthorityService")
    public void deleteAuthority(Long authorityId) {
        authorityMapper.deleteByPrimaryKey(authorityId);
    }

    @Override
    public Page<Authority> findPageAuthority(Query<Authority> query) {

        AuthorityExample example = new AuthorityExample();
        AuthorityExample.Criteria criteria = example.createCriteria();
        Authority authority = query.getData();
        if (authority.getId() != null) {
            criteria.andIdEqualTo(authority.getId());
        }
        if (StringUtils.isNotBlank(authority.getName())) {
            criteria.andNameLike("'" + authority.getName() + "%'");
        }

        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getLimit());

        List<Authority> authorityList = authorityMapper.selectByExampleWithRowbounds(example, rowBounds);

        long total = authorityMapper.countByExample(example);

        Page<Authority> authorityPage = Page.of(query);
        authorityPage.setTotalElements(total);
        authorityPage.setContent(authorityList);

        return authorityPage;
    }
}
