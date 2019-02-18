package com.zooms.dean.auth.domain;

import com.zooms.dean.common.domain.BaseEntity;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table auth_company
 *
 * @mbg.generated do_not_delete_during_merge Wed May 30 10:29:17 CST 2018
 */
public class Company extends BaseEntity {
    /**
     * Database Column Remarks:
     *   编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_company.id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   父级编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_company.parent_id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    private Long parentId;

    /**
     * Database Column Remarks:
     *   父公司名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_company.parent_name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    private String parentName;

    /**
     * Database Column Remarks:
     *   所有父级编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_company.path
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    private String path;

    /**
     * Database Column Remarks:
     *   公司名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth_company.name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_company.id
     *
     * @return the value of auth_company.id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_company.id
     *
     * @param id the value for auth_company.id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_company.parent_id
     *
     * @return the value of auth_company.parent_id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_company.parent_id
     *
     * @param parentId the value for auth_company.parent_id
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_company.parent_name
     *
     * @return the value of auth_company.parent_name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_company.parent_name
     *
     * @param parentName the value for auth_company.parent_name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_company.path
     *
     * @return the value of auth_company.path
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_company.path
     *
     * @param path the value for auth_company.path
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth_company.name
     *
     * @return the value of auth_company.name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth_company.name
     *
     * @param name the value for auth_company.name
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_company
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
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
        Company other = (Company) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getParentName() == null ? other.getParentName() == null : this.getParentName().equals(other.getParentName()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_company
     *
     * @mbg.generated Wed May 30 10:29:17 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getParentName() == null) ? 0 : getParentName().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }
}