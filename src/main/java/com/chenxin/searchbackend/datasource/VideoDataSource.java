package com.chenxin.searchbackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/26 23:10
 * @modify
 */
@Service
public class VideoDataSource implements DataSource {

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
