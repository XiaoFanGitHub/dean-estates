package com.zooms.dean.auth.mapper;

import com.zooms.dean.auth.domain.UserAuthorityExample;
import com.zooms.dean.auth.domain.UserAuthorityKey;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserAuthorityMapper {

    /**
     * 根据用户权限对象查询出用户对应的所有权限
     *
     * @param example 查询对象
     * @return 用户权限计划
     */
    @SelectProvider(type = UserAuthoritySqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "authority_id", property = "authorityId", jdbcType = JdbcType.VARCHAR, id = true)
    })
    List<UserAuthorityKey> selectByExample(UserAuthorityExample example);

}