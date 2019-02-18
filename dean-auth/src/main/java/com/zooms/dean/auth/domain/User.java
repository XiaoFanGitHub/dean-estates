package com.zooms.dean.auth.domain;

import com.zooms.dean.common.domain.BaseEntity;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table auth_user
 *
 * @mbg.generated do_not_delete_during_merge Fri Nov 09 10:54:18 CST 2018
 */
public class User extends BaseEntity {
    /**
     * Database Column Remarks:
     *   编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   归属公司
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.company_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Long companyId;

    /**
     * Database Column Remarks:
     *   归属部门
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.office_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Long officeId;

    /**
     * Database Column Remarks:
     *   职位ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.position_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Long positionId;

    /**
     * Database Column Remarks:
     *   登录名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.username
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String username;

    /**
     * Database Column Remarks:
     *   密码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.password
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String password;

    /**
     * Database Column Remarks:
     *   客户编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.user_no
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String userNo;

    /**
     * Database Column Remarks:
     *   姓名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.name
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String name;

    /**
     * Database Column Remarks:
     *   邮箱
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.email
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String email;

    /**
     * Database Column Remarks:
     *   电话
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.phone
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String phone;

    /**
     * Database Column Remarks:
     *   手机
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.mobile
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String mobile;

    /**
     * Database Column Remarks:
     *   用户头像地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.avatar
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String avatar;

    /**
     * Database Column Remarks:
     *   用户类型
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.user_type
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String userType;

    /**
     * Database Column Remarks:
     *   最后登陆IP
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.login_ip
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String loginIp;

    /**
     * Database Column Remarks:
     *   最后登陆时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.login_date
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Date loginDate;

    /**
     * Database Column Remarks:
     *   微信授权用户唯一标识
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.wechat_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String wechatOpenid;

    /**
     * Database Column Remarks:
     *   QQ授权用户唯一标识
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.qq_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String qqOpenid;

    /**
     * Database Column Remarks:
     *   设备ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.device_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String deviceId;

    /**
     * Database Column Remarks:
     *   SAP账号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.sap_account
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private String sapAccount;

    /**
     * Database Column Remarks:
     *   是否第一次登陆 0 第一次
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user.first_login
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    private Long firstLogin;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.id
     *
     * @return the value of auth_user.id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.id
     *
     * @param id the value for auth_user.id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.company_id
     *
     * @return the value of auth_user.company_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.company_id
     *
     * @param companyId the value for auth_user.company_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.office_id
     *
     * @return the value of auth_user.office_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Long getOfficeId() {
        return officeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.office_id
     *
     * @param officeId the value for auth_user.office_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.position_id
     *
     * @return the value of auth_user.position_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.position_id
     *
     * @param positionId the value for auth_user.position_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.username
     *
     * @return the value of auth_user.username
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.username
     *
     * @param username the value for auth_user.username
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.password
     *
     * @return the value of auth_user.password
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.password
     *
     * @param password the value for auth_user.password
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.user_no
     *
     * @return the value of auth_user.user_no
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.user_no
     *
     * @param userNo the value for auth_user.user_no
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.name
     *
     * @return the value of auth_user.name
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.name
     *
     * @param name the value for auth_user.name
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.email
     *
     * @return the value of auth_user.email
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.email
     *
     * @param email the value for auth_user.email
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.phone
     *
     * @return the value of auth_user.phone
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.phone
     *
     * @param phone the value for auth_user.phone
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.mobile
     *
     * @return the value of auth_user.mobile
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.mobile
     *
     * @param mobile the value for auth_user.mobile
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.avatar
     *
     * @return the value of auth_user.avatar
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.avatar
     *
     * @param avatar the value for auth_user.avatar
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.user_type
     *
     * @return the value of auth_user.user_type
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.user_type
     *
     * @param userType the value for auth_user.user_type
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.login_ip
     *
     * @return the value of auth_user.login_ip
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.login_ip
     *
     * @param loginIp the value for auth_user.login_ip
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.login_date
     *
     * @return the value of auth_user.login_date
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.login_date
     *
     * @param loginDate the value for auth_user.login_date
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.wechat_openid
     *
     * @return the value of auth_user.wechat_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getWechatOpenid() {
        return wechatOpenid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.wechat_openid
     *
     * @param wechatOpenid the value for auth_user.wechat_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid == null ? null : wechatOpenid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.qq_openid
     *
     * @return the value of auth_user.qq_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getQqOpenid() {
        return qqOpenid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.qq_openid
     *
     * @param qqOpenid the value for auth_user.qq_openid
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid == null ? null : qqOpenid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.device_id
     *
     * @return the value of auth_user.device_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.device_id
     *
     * @param deviceId the value for auth_user.device_id
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.sap_account
     *
     * @return the value of auth_user.sap_account
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public String getSapAccount() {
        return sapAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.sap_account
     *
     * @param sapAccount the value for auth_user.sap_account
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setSapAccount(String sapAccount) {
        this.sapAccount = sapAccount == null ? null : sapAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user.first_login
     *
     * @return the value of auth_user.first_login
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public Long getFirstLogin() {
        return firstLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user.first_login
     *
     * @param firstLogin the value for auth_user.first_login
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    public void setFirstLogin(Long firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getOfficeId() == null ? other.getOfficeId() == null : this.getOfficeId().equals(other.getOfficeId()))
            && (this.getPositionId() == null ? other.getPositionId() == null : this.getPositionId().equals(other.getPositionId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getLoginIp() == null ? other.getLoginIp() == null : this.getLoginIp().equals(other.getLoginIp()))
            && (this.getLoginDate() == null ? other.getLoginDate() == null : this.getLoginDate().equals(other.getLoginDate()))
            && (this.getWechatOpenid() == null ? other.getWechatOpenid() == null : this.getWechatOpenid().equals(other.getWechatOpenid()))
            && (this.getQqOpenid() == null ? other.getQqOpenid() == null : this.getQqOpenid().equals(other.getQqOpenid()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getSapAccount() == null ? other.getSapAccount() == null : this.getSapAccount().equals(other.getSapAccount()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getFirstLogin() == null ? other.getFirstLogin() == null : this.getFirstLogin().equals(other.getFirstLogin()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user
     *
     * @mbg.generated Fri Nov 09 10:54:18 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getOfficeId() == null) ? 0 : getOfficeId().hashCode());
        result = prime * result + ((getPositionId() == null) ? 0 : getPositionId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getLoginIp() == null) ? 0 : getLoginIp().hashCode());
        result = prime * result + ((getLoginDate() == null) ? 0 : getLoginDate().hashCode());
        result = prime * result + ((getWechatOpenid() == null) ? 0 : getWechatOpenid().hashCode());
        result = prime * result + ((getQqOpenid() == null) ? 0 : getQqOpenid().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getSapAccount() == null) ? 0 : getSapAccount().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getFirstLogin() == null) ? 0 : getFirstLogin().hashCode());
        return result;
    }
}