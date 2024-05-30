package com.chenxin.searchbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxin.searchbackend.model.entity.Picture;

/**
 * @author fangchenxin
 * @description 图片服务
 * @date 2024/5/26 00:04
 * @modify
 */
public interface PictureService {

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
