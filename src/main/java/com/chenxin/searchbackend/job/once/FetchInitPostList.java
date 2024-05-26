package com.chenxin.searchbackend.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chenxin.searchbackend.model.entity.Post;
import com.chenxin.searchbackend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fangchenxin
 * @description 每次重启Springboot项目时，执行一次方法
 * @date 2024/5/25 20:23
 * @modify
 */
@Slf4j
//@Component
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"tags\": [],\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://api.code-nav.cn/api/post/search/page/vo";
        String body = HttpRequest.post(url).body(json).execute().body();
        Map<String, Object> map = JSONUtil.toBean(body, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject temp = (JSONObject) record;
            Post post = new Post();
            post.setTitle(temp.getStr("title"));
            post.setContent(temp.getStr("content"));
            // 标签
            JSONArray tags = temp.getJSONArray("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
        boolean res = postService.saveBatch(postList);
        log.info("获取初始化帖子成功， 条数={}", postList.size());
    }
}
