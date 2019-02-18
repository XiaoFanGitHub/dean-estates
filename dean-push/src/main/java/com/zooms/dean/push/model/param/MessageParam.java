package com.zooms.dean.push.model.param;

import javax.validation.constraints.NotNull;

/**
 * 输入消息参数
 * 
 * @author zhaolijin
 * @date 2018年5月12日
 */
public class MessageParam {
    /**
     * 发送用户ID,可以为空
     */
    private Integer fromUserId;

    /**
     * 接收用户ID
     */
    @NotNull(message = "接收用户ID不能为空")
    private Integer toUserId;
    
    /**
     * 接收用户
     */
    @NotNull(message = "接收用户不能为空")
    private String toUserName;

    /**
     * 消息类型，参考MessageType
     */
    @NotNull(message = "消息类型ID不能为空")
    private Integer type;

    /**
     * 消息标题
     */
    @NotNull(message = "消息标题不能为空")
    private String title;

    /**
     * 消息内容
     */
    @NotNull(message = "消息标题不能为空")
    private String body;

    /**
     * 跳转类型，参考GoType
     */
    private Integer goType;

    /**
     * 对应的参数值，比如订单主体ID
     */
    private String goValue;

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getGoType() {
        return goType;
    }

    public void setGoType(Integer goType) {
        this.goType = goType;
    }

    public String getGoValue() {
        return goValue;
    }

    public void setGoValue(String goValue) {
        this.goValue = goValue;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

}
