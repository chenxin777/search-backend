package com.chenxin.searchbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxin.searchbackend.common.BaseResponse;
import com.chenxin.searchbackend.common.ErrorCode;
import com.chenxin.searchbackend.common.ResultUtils;
import com.chenxin.searchbackend.exception.BusinessException;
import com.chenxin.searchbackend.model.dto.post.PostQueryRequest;
import com.chenxin.searchbackend.model.dto.search.SearchRequest;
import com.chenxin.searchbackend.model.dto.user.UserQueryRequest;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.model.vo.PostVO;
import com.chenxin.searchbackend.model.vo.SearchVO;
import com.chenxin.searchbackend.model.vo.UserVO;
import com.chenxin.searchbackend.service.PictureService;
import com.chenxin.searchbackend.service.PostService;
import com.chenxin.searchbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private PictureService pictureService;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearch(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
            Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
            return picturePage;
        });

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
            return userVOPage;
        });

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
            return postVOPage;
        });

        CompletableFuture.allOf(pictureTask, userTask, postTask).join();
        try {
            Page<Picture> picturePage = pictureTask.get();
            Page<PostVO> postVOPage = postTask.get();
            Page<UserVO> userVOPage = userTask.get();
            SearchVO searchVO = new SearchVO();
            searchVO.setPictureList(picturePage.getRecords());
            searchVO.setUserVOList(userVOPage.getRecords());
            searchVO.setPostVOList(postVOPage.getRecords());
            return ResultUtils.success(searchVO);
        } catch (Exception ex) {
            log.error("查询异常", ex);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

}
