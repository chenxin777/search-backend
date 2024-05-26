package com.chenxin.searchbackend.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxin.searchbackend.common.ErrorCode;
import com.chenxin.searchbackend.exception.BusinessException;
import com.chenxin.searchbackend.model.entity.Picture;
import com.chenxin.searchbackend.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/26 00:16
 * @modify
 */
@Service
public class PictureServiceImpl implements PictureService {

    /**
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.chenxin.searchbackend.model.entity.Picture>
     * @description 图片搜索服务
     * @author fangchenxin
     * @date 2024/5/26 00:47
     */
    @Override
    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://www.bing.com/images/search?q=%s&qpvt=image&form=IGRE&first=%s&cw=1177&ch=824", searchText, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
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
            if (pictureList.size() >= pageSize) {
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
