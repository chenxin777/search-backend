package com.chenxin.searchbackend.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fangchenxin
 * @description 图片
 * @date 2024/5/25 22:32
 * @modify
 */
@Data
public class Picture implements Serializable {

    private static final long serialVersionUID = -1077796296903102168L;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String url;


}
