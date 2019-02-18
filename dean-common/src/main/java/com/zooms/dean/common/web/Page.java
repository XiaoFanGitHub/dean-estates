package com.zooms.dean.common.web;

import java.io.Serializable;
import java.util.List;

/**
 * com.nondo.superp.core.endpoint.model
 *
 * @author linfeng
 * @since 2017/7/30
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 8041105109091951223L;
    private final int page;
    private final int size;
    private long totalPages;
    private long totalElements;
    private List<T> content;

    public Page() {
        this.page = 0;
        this.size = 10;
    }

    public Page(int pageNumber, int pageSize) {

        if (pageNumber >= 1) {
            this.page = pageNumber;
        } else {
            this.page = 0;
        }
        this.size = pageSize;
    }

    public Page(int pageNumber, int pageSize, long totalElements) {

        this(pageNumber, pageSize);
        this.totalPages = (totalElements / pageSize + 1);
        this.totalElements = totalElements;
    }

    public static <T> Page<T> of(Query<T> query) {
        return new Page<>(query.getPage(), query.getSize());
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (totalElements / size + 1);
    }
}
