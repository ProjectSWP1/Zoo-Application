package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.ZooNewsDto;
import com.thezookaycompany.zookayproject.model.entity.ZooNews;

import java.util.List;

public interface ZooNewsService {

    // Tạo news
    String postNews(ZooNewsDto zooNewsDto);

    List<ZooNews> getNews();
}
