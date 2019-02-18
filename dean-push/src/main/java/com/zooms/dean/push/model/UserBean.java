package com.zooms.dean.push.model;


public class UserBean {
    /**
     * 经销商ID
     */
    private Integer id;
    /**
     * 等级ID
     */
    private Integer gradeId;
    /**
     * 店铺ID
     */
    private Integer storeId;
    /**
     * 经销商编号
     */
    private String userNo;
    /**
     * 经销商名称
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 区域编码
     */
    private String regionCode;
    /**
     * 接单员姓名
     */
    private String orderTakeUser;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the gradeId
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the gradeId to set
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the userNo
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @param regionCode the regionCode to set
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * @return the orderTakeUser
     */
    public String getOrderTakeUser() {
        return orderTakeUser;
    }

    /**
     * @param orderTakeUser the orderTakeUser to set
     */
    public void setOrderTakeUser(String orderTakeUser) {
        this.orderTakeUser = orderTakeUser;
    }

}
