package com.chenxin.searchbackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * @author fangchenxin
 * @description 数据源接口（新接入的数据源必须实现）
 * @date 2024/5/26 21:55
 * @modify
 */
public interface DataSource<T> {

    Page<T> doSearch(String searchText, long pageNum, long pageSize);

}
