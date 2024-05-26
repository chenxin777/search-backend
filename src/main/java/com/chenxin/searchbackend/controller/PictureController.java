package com.chenxin.searchbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxin.searchbackend.common.BaseResponse;
import com.chenxin.searchbackend.common.ErrorCode;
import com.chenxin.searchbackend.common.ResultUtils;
import com.chenxin.searchbackend.exception.ThrowUtils;
import com.chenxin.searchbackend.model.dto.picture.PictureQueryRequest;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        long pageNum = pictureQueryRequest.getCurrent();
        long pageSize = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(pictureService.searchPicture(searchText, pageNum, pageSize));
    }

}
