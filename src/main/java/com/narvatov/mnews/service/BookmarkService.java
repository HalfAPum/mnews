package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.BookmarkDao;
import com.narvatov.mnews.dao.BookmarkedNewsDao;
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO;
import com.narvatov.mnews.mapper.MapperKt;
import com.narvatov.mnews.model.News;
import com.narvatov.mnews.model.auth.User;
import com.narvatov.mnews.model.bookmark.BookmarkedNews;
import com.narvatov.mnews.service.auth.UserService;
import com.narvatov.mnews.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkDao dao;
    @Autowired
    private UserService userService;
    @Autowired
    private BookmarkedNewsDao bookmarkedNewsDao;

    public String add(String authHeader, int newsId) {
        User user = userService.extractUserFromAuthHeader(authHeader);

        BookmarkedNews bookmarkedNews = new BookmarkedNews(user.getId(), newsId);

        dao.save(bookmarkedNews);

        return ServerResponse.getCreatedMessage(bookmarkedNews);
    }

    public String delete(String authHeader, int newsId) {
        User user = userService.extractUserFromAuthHeader(authHeader);

        dao.delete(new BookmarkedNews(user.getId(), newsId));

        return ServerResponse.getDeletedMessage(newsId);
    }

    public List<SimpleNewsDTO> getAll(String authHeader) {
        User user = userService.extractUserFromAuthHeader(authHeader);

        List<News> bookmarkedNews = bookmarkedNewsDao.findAllBookmarkedNews(user.getId());

        return MapperKt.mapToSimpleNewsDTO(bookmarkedNews);
    }

}
