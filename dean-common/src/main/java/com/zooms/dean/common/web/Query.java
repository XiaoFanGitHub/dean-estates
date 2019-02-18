package com.zooms.dean.common.web;

import java.io.Serializable;

/**
 * 查询对象
 *
 * @author linfeng
 * @since 2017/8/5
 */
public class Query<T> implements Serializable {

    private Integer page = 1;
    private Integer size = 10;
    private T data;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getOffset() {
        return (page - 1) * size;
    }

    public Integer getLimit() {
        return size;
    }

}
