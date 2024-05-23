package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.NewsDao;
import com.narvatov.mnews.dto.request.news.CreateNews;
import com.narvatov.mnews.dto.response.news.DetailedNewsDTO;
import com.narvatov.mnews.mapper.MapperKt;
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO;
import com.narvatov.mnews.model.News;
import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.auth.UserService;
import com.narvatov.mnews.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDao dao;
    @Autowired
    private UserService userService;

    public List<SimpleNewsDTO> getAll() {
        List<News> news = dao.findAllNoCommentsSortedByDate();

        return MapperKt.mapToSimpleNewsDTO(news);
    }

    public List<SimpleNewsDTO> getByCategory(String category) {
        List<News> news = dao.findByCategory(category);

        return MapperKt.mapToSimpleNewsDTO(news);
    }

    public DetailedNewsDTO getDetailedNewsById(int id) {
        News news = dao.findById(id).orElseThrow(() -> new IllegalArgumentException("Id is not present in news table"));

        return new DetailedNewsDTO(news);
    }

    public SimpleNewsDTO getSimpleNewsById(int id) {
        News news = dao.findById(id).orElseThrow(() -> new IllegalArgumentException("Id is not present in news table"));

        return new SimpleNewsDTO(news);
    }


    public String add(String authHeader, CreateNews createNews) {
        User admin = userService.extractUserFromAuthHeader(authHeader);

        dao.save(createNews.getNews(admin));

        return ServerResponse.getCreatedMessage(createNews);
    }


    public String update(News news) {
        dao.save(news);

        return ServerResponse.getUpdatedMessage(news);
    }


    public String delete(int id) {
        dao.deleteById(id);

        return ServerResponse.getDeletedMessage(id);
    }

}
