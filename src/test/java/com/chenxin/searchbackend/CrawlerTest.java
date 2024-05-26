package com.chenxin.searchbackend;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.model.entity.Post;
import com.chenxin.searchbackend.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/25 19:35
 * @modify
 */
@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPassage() {
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
        Assertions.assertTrue(res);
    }

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = String.format("https://www.bing.com/images/search?q=image&qpvt=image&form=IGRE&first=%s&cw=1177&ch=824", current);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            // 图片地址（murl）
            String attr = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            String murl = StrUtil.toString(JSONUtil.toBean(attr, Map.class).get("murl"));
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureList.add(picture);
        }

    }

}
