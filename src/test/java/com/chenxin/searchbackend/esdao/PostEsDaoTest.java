package com.chenxin.searchbackend.esdao;

import com.chenxin.searchbackend.model.dto.post.PostEsDTO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/27 21:07
 * @modify
 */
@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(3L);
        postEsDTO.setTitle("行走的CD888hhhhhhhhhhhh");
        postEsDTO.setContent("林俊杰就是行走的CD888hhhhhhhhhhhhhh");
        postEsDTO.setTags(Lists.newArrayList("JJ", "Music"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        PostEsDTO res = postEsDao.save(postEsDTO);
        System.out.println(res.getId());
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> postEsDTOPage = postEsDao.findAll(PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postEsDTOList = postEsDTOPage.getContent();
        System.out.println(postEsDTOList);

    }

    @Test
    void testDelete() {
        postEsDao.deleteAll();
    }

    @Test
    void testFindByTitle() {
        List<PostEsDTO> postEsDTOList = postEsDao.findByTitle("CD");
        System.out.println(postEsDTOList);
    }
}
