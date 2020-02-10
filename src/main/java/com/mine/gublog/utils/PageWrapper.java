package com.mine.gublog.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页信息包装器
 */
@Data
public class PageWrapper<T> implements Serializable {
    /**
     * 状态码  0：成功
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;
    /**
     * 数据总数
     */
    private Long count;
    /**
     * 数据
     */
    private T data;
}
