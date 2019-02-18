package com.zooms.dean.push.model.result;


public class UserMessageResult {
    /**
     * 用户消息ID
     */
    private Integer id;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String body;

    /**
     * 跳转类型
     */
    private Integer goType;

    /**
     * 对应的参数值
     */
    private String goValue;

    /**
     * 发送用户ID
     */
    private Integer fromUserId;

    /**
     * 接收用户ID
     */
    private Integer toUserId;

    /**
     * 状态：0.未读；1已读
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private String createDate;
    
    /**
     * 扩展参数
     */
    private String extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createTime) {
        this.createDate = createTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
