package com.zooms.dean.auth.mapper;

import com.zooms.dean.auth.domain.App;
import com.zooms.dean.auth.domain.AppExample;
import com.zooms.dean.common.mybatis.Table;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Table("auth_app")
@Mapper
public interface AppMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AppSqlProvider.class, method="countByExample")
    long countByExample(AppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @DeleteProvider(type=AppSqlProvider.class, method="deleteByExample")
    int deleteByExample(AppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Delete({
        "delete from auth_app",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Insert({
        "insert into auth_app (app_key, app_name, ",
        "not_update)",
        "values (#{appKey,jdbcType=VARCHAR}, #{appName,jdbcType=VARCHAR}, ",
        "#{notUpdate,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(App record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @InsertProvider(type=AppSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(App record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AppSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="app_key", property="appKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="app_name", property="appName", jdbcType=JdbcType.VARCHAR),
        @Result(column="not_update", property="notUpdate", jdbcType=JdbcType.INTEGER)
    })
    List<App> selectByExampleWithRowbounds(AppExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @SelectProvider(type=AppSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="app_key", property="appKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="app_name", property="appName", jdbcType=JdbcType.VARCHAR),
        @Result(column="not_update", property="notUpdate", jdbcType=JdbcType.INTEGER)
    })
    List<App> selectByExample(AppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Select({
        "select",
        "id, app_key, app_name, not_update",
        "from auth_app",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="app_key", property="appKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="app_name", property="appName", jdbcType=JdbcType.VARCHAR),
        @Result(column="not_update", property="notUpdate", jdbcType=JdbcType.INTEGER)
    })
    App selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AppSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") App record, @Param("example") AppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AppSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") App record, @Param("example") AppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @UpdateProvider(type=AppSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(App record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_app
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Update({
        "update auth_app",
        "set app_key = #{appKey,jdbcType=VARCHAR},",
          "app_name = #{appName,jdbcType=VARCHAR},",
          "not_update = #{notUpdate,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(App record);
}