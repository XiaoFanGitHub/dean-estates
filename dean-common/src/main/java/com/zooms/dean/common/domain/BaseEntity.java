package com.zooms.dean.common.domain;


import com.zooms.dean.common.annotation.CreatedBy;
import com.zooms.dean.common.annotation.CreatedDate;
import com.zooms.dean.common.annotation.LastModifiedBy;
import com.zooms.dean.common.annotation.LastModifiedDate;

import java.util.Date;

/**
 * 模型基础类
 *
 * @author linfeng
 */
public class BaseEntity {

    /**
     * 备注
     */
    protected String remarks;
    /**
     * 创建者
     */
    @CreatedBy
    protected Long createBy;
    /**
     * 创建日期
     */
    @CreatedDate
    protected Date createDate;
    /**
     * 更新者
     */
    @LastModifiedBy
    protected Long updateBy;
    /**
     * 更新日期
     */
    @LastModifiedDate
    protected Date updateDate;
    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    protected String delFlag;

    /**
     * 正常
     */
    public static final String DEL_FLAG_NORMAL = "0";
    /**
     * 删除
     */
    public static final String DEL_FLAG_DELETE = "1";

    /**
     * 字符串格式的创建日期
     */
    protected String foundDate;

    /**
     * 字符串类型的修改日期
     */
    protected String editDate;

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
