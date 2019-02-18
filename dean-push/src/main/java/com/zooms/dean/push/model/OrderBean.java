package com.zooms.dean.push.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBean {
    /**
     * 订单ID
     */
    private Integer id;
    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 金额合计
     */
    private BigDecimal amount;

    /**
     * 下单时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
