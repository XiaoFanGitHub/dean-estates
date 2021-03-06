package com.zooms.dean.push.mapper;

import com.nondo.dean.common.mybatis.Table;
import com.zooms.dean.push.domain.UserMessage;
import com.zooms.dean.push.domain.UserMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

@Table("user_message")
@Mapper
public interface UserMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @SelectProvider(type=UserMessageSqlProvider.class, method="countByExample")
    long countByExample(UserMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @DeleteProvider(type=UserMessageSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @Delete({
        "delete from user_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @Insert({
        "insert into user_message (id, from_user_id, ",
        "to_user_id, message_id, ",
        "status, create_by, ",
        "create_date, update_by, ",
        "update_date, remarks, ",
        "del_flag)",
        "values (#{id,jdbcType=INTEGER}, #{fromUserId,jdbcType=INTEGER}, ",
        "#{toUserId,jdbcType=INTEGER}, #{messageId,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{createBy,jdbcType=BIGINT}, ",
        "#{createDate,jdbcType=TIMESTAMP}, #{updateBy,jdbcType=BIGINT}, ",
        "#{updateDate,jdbcType=TIMESTAMP}, #{remarks,jdbcType=VARCHAR}, ",
        "#{delFlag,jdbcType=CHAR})"
    })
    int insert(UserMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @InsertProvider(type=UserMessageSqlProvider.class, method="insertSelective")
    int insertSelective(UserMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @SelectProvider(type=UserMessageSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="from_user_id", property="fromUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="to_user_id", property="toUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="message_id", property="messageId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    List<UserMessage> selectByExampleWithRowbounds(UserMessageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @SelectProvider(type=UserMessageSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="from_user_id", property="fromUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="to_user_id", property="toUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="message_id", property="messageId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    List<UserMessage> selectByExample(UserMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @Select({
        "select",
        "id, from_user_id, to_user_id, message_id, status, create_by, create_date, update_by, ",
        "update_date, remarks, del_flag",
        "from user_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="from_user_id", property="fromUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="to_user_id", property="toUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="message_id", property="messageId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_by", property="createBy", jdbcType=JdbcType.BIGINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_by", property="updateBy", jdbcType=JdbcType.BIGINT),
        @Result(column="update_date", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="del_flag", property="delFlag", jdbcType=JdbcType.CHAR)
    })
    UserMessage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @UpdateProvider(type=UserMessageSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserMessage record, @Param("example") UserMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @UpdateProvider(type=UserMessageSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserMessage record, @Param("example") UserMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @UpdateProvider(type=UserMessageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated Mon Jan 07 11:28:04 CST 2019
     */
    @Update({
        "update user_message",
        "set from_user_id = #{fromUserId,jdbcType=INTEGER},",
          "to_user_id = #{toUserId,jdbcType=INTEGER},",
          "message_id = #{messageId,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "create_by = #{createBy,jdbcType=BIGINT},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "update_by = #{updateBy,jdbcType=BIGINT},",
          "update_date = #{updateDate,jdbcType=TIMESTAMP},",
          "remarks = #{remarks,jdbcType=VARCHAR},",
          "del_flag = #{delFlag,jdbcType=CHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserMessage record);
}