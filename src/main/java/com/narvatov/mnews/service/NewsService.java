package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.NewsDao;
import com.narvatov.mnews.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    public NewsDao dao;

    //return news without comments
    public List<News> getAll() {
        return dao.findAll(getSortDESCByDate());
    }

    public List<News> getByCategory(String category) {
        return dao.findByCategory(category, getSortDESCByDate());
    }

    private Sort getSortDESCByDate() {
        return Sort.by(new Sort.Order(Sort.Direction.DESC, "date"));
    }

    //return news with comments
    public News get(int id) {
        return dao.findById(id).orElse(null);
    }


    public String add(News news) {
        News createdNews = dao.save(news);

        return "News " + createdNews.id + " has been successfully created.";
    }


    public String update(News news) {
        News updatedNews = dao.save(news);

        return "News " + updatedNews.id + " has been successfully updated.";
    }


    public String delete(int id) {
        dao.deleteById(id);

        return "News " + id + " has been successfully deleted.";
    }

}
