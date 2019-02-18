package com.zooms.dean.push.mapper;

import com.zooms.dean.push.domain.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMessageExtendsMapper {

    /**
     * 查询发给用户的某种类型的消息
     * @param userId 用户ID
     * @param type 消息类型
     * @return
     */
    @Select({"SELECT * FROM user_message um INNER JOIN message m ON um.message_id=m.id ",
        "WHERE um.to_user_id=${userId} AND m.type=${type}"})
    List<UserMessage> getUserMessageListByType(@Param(value = "userId") int userId, @Param(value = "type") int type);
}