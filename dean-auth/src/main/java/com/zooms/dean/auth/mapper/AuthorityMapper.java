package com.zooms.dean.auth.mapper;

import com.zooms.dean.auth.domain.Authority;
import com.zooms.dean.auth.domain.AuthorityExample;
import com.zooms.dean.common.mybatis.Table;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Table("auth_authority")
@Mapper
public interface AuthorityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AuthoritySqlProvider.class, method="countByExample")
    long countByExample(AuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @DeleteProvider(type=AuthoritySqlProvider.class, method="deleteByExample")
    int deleteByExample(AuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Delete({
        "delete from auth_authority",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Insert({
        "insert into auth_authority (code, name, ",
        "table_name, domain_force, ",
        "global, create_date, ",
        "update_date, remarks, ",
        "del_flag)",
        "values (#{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{tableName,jdbcType=VARCHAR}, #{domainForce,jdbcType=VARCHAR}, ",
        "#{global,jdbcType=BIT}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{remarks,jdbcType=VARCHAR}, ",
        "#{delFlag,jdbcType=CHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Authority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @InsertProvider(type=AuthoritySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Authority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AuthoritySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="table_name", property="tableName", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain_force", property="domainForce", jdbcType=JdbcType.VARCHAR),
        @Result(column="global", property="global", jdbcType=JdbcType.BIT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    List<Authority> selectByExampleWithRowbounds(AuthorityExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AuthoritySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="table_name", property="tableName", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain_force", property="domainForce", jdbcType=JdbcType.VARCHAR),
        @Result(column="global", property="global", jdbcType=JdbcType.BIT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    List<Authority> selectByExample(AuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Select({
        "select",
        "id, code, name, table_name, domain_force, global, create_date, update_date, ",
        "remarks, del_flag",
        "from auth_authority",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="table_name", property="tableName", jdbcType=JdbcType.VARCHAR),
        @Result(column="domain_force", property="domainForce", jdbcType=JdbcType.VARCHAR),
        @Result(column="global", property="global", jdbcType=JdbcType.BIT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    Authority selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Authority record, @Param("example") AuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Authority record, @Param("example") AuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Authority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_authority
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Update({
        "update auth_authority",
        "set code = #{code,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "table_name = #{tableName,jdbcType=VARCHAR},",
          "domain_force = #{domainForce,jdbcType=VARCHAR},",
          "global = #{global,jdbcType=BIT},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "update_date = #{updateDate,jdbcType=TIMESTAMP},",
          "remarks = #{remarks,jdbcType=VARCHAR},",
          "del_flag = #{delFlag,jdbcType=CHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Authority record);
}