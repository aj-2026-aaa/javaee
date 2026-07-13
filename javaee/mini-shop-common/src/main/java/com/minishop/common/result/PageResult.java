package com.minishop.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long total;
    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;

    public PageResult() {
        this.total = 0L;
        this.list = Collections.emptyList();
        this.pageNum = 1;
        this.pageSize = 10;
        this.pages = 0;
    }

    public PageResult(Long total, List<T> list, Integer pageNum, Integer pageSize, Integer pages) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
    }
}
