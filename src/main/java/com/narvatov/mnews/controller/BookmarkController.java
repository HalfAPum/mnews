package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.bookmark.CreateBookmark;
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO;
import com.narvatov.mnews.model.News;
import com.narvatov.mnews.model.auth.annotation.UserAuthorized;
import com.narvatov.mnews.service.BookmarkService;
import com.narvatov.mnews.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService service;

    @PostMapping("add")
    @UserAuthorized
    public String add(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @RequestBody CreateBookmark createBookmark) {
        return service.add(authHeader, createBookmark.getNewsId());
    }

    @DeleteMapping("delete/{id}")
    @UserAuthorized
    public String delete(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @PathVariable("id") int id) {
        return service.delete(authHeader, id);
    }

    @GetMapping("getAll")
    @UserAuthorized
    public List<SimpleNewsDTO> getAll(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader) {
        return service.getAll(authHeader);
    }

}
