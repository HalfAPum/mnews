package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.NewsDao;
import com.narvatov.mnews.model.News;
import com.narvatov.mnews.dto.response.SimpleNewsDTO;
import com.narvatov.mnews.utils.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsDao dao;

    public List<SimpleNewsDTO> getAll() {
        return dao.findAllNoCommentsSortedByDate();
    }

    public List<News> getByCategory(String category) {
        return dao.findByCategory(category);
    }

    public News get(int id) {
        return dao.findById(id).orElse(null);
    }


    public String add(News news) {
        dao.save(news);

        return ServerResponse.getCreatedMessage(news);
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
