package com.chenxin.searchbackend.controller;

import com.chenxin.searchbackend.common.BaseResponse;
import com.chenxin.searchbackend.common.ResultUtils;
import com.chenxin.searchbackend.manager.SearchFacade;
import com.chenxin.searchbackend.model.dto.search.SearchRequest;
import com.chenxin.searchbackend.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 搜索接口
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearch(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.doSearch(searchRequest, request));
    }

}
