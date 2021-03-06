package com.zooms.dean.auth.mapper;

import com.zooms.dean.auth.domain.UserRoleExample;
import com.zooms.dean.auth.domain.UserRoleKey;
import com.zooms.dean.common.mybatis.Table;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Table("auth_user_role")
@Mapper
public interface UserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @SelectProvider(type=UserRoleSqlProvider.class, method="countByExample")
    long countByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @DeleteProvider(type=UserRoleSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @Delete({
        "delete from auth_user_role",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(UserRoleKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @Insert({
        "insert into auth_user_role (user_id, role_id)",
        "values (#{userId,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT})"
    })
    int insert(UserRoleKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @InsertProvider(type=UserRoleSqlProvider.class, method="insertSelective")
    int insertSelective(UserRoleKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @SelectProvider(type=UserRoleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true)
    })
    List<UserRoleKey> selectByExampleWithRowbounds(UserRoleExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @SelectProvider(type=UserRoleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true)
    })
    List<UserRoleKey> selectByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @UpdateProvider(type=UserRoleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserRoleKey record, @Param("example") UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_role
     *
     * @mbg.generated Wed May 30 10:45:08 CST 2018
     */
    @UpdateProvider(type=UserRoleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserRoleKey record, @Param("example") UserRoleExample example);
}