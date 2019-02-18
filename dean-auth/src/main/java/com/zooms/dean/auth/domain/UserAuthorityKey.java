package com.zooms.dean.auth.domain;

import com.zooms.dean.common.domain.BaseEntity;


/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table auth_user_authority
 *
 * @mbg.generated do_not_delete_during_merge Sun Apr 08 14:20:17 CST 2018
 */
public class UserAuthorityKey extends BaseEntity {
    /**
     * Database Column Remarks:
     *   用户编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user_authority.user_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    private Long userId;

    /**
     * Database Column Remarks:
     *   权限编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_user_authority.authority_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    private String authorityId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user_authority.user_id
     *
     * @return the value of auth_user_authority.user_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user_authority.user_id
     *
     * @param userId the value for auth_user_authority.user_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_user_authority.authority_id
     *
     * @return the value of auth_user_authority.authority_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    public String getAuthorityId() {
        return authorityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_user_authority.authority_id
     *
     * @param authorityId the value for auth_user_authority.authority_id
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId == null ? null : authorityId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_authority
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
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
        UserAuthorityKey other = (UserAuthorityKey) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAuthorityId() == null ? other.getAuthorityId() == null : this.getAuthorityId().equals(other.getAuthorityId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_user_authority
     *
     * @mbg.generated Sun Apr 08 14:20:17 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAuthorityId() == null) ? 0 : getAuthorityId().hashCode());
        return result;
    }
}