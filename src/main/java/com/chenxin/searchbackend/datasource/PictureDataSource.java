package com.chenxin.searchbackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/26 00:16
 * @modify
 */
@Service
public class PictureDataSource implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        return pictureService.searchPicture(searchText, pageNum, pageSize);
    }

}
