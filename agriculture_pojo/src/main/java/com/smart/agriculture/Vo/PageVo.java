package com.smart.agriculture.Vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {
    /**
     * 分页数据
     */

    private List<T> data;
    /**
     * 当前页数
     */
    private long pageNum;
    /**
     * 每页条数
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 总条数
     */
    private long totalSize;
}