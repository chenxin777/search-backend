package com.chenxin.searchbackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.model.entity.Post;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 聚合搜索返回结果
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@Data
public class SearchVO implements Serializable {

    private final static Gson gson = new Gson();

    private List<UserVO> userVOList;

    private List<PostVO> postVOList;

    private List<Picture> pictureList;


}
