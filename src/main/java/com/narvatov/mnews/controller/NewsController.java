package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.news.CreateNews;
import com.narvatov.mnews.dto.response.news.DetailedNewsDTO;
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO;
import com.narvatov.mnews.model.auth.annotation.AdminAuthorized;
import com.narvatov.mnews.service.NewsService;
import com.narvatov.mnews.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {

    @Autowired
    private NewsService service;


    //Provide paging
    @GetMapping("getAll")
    public List<SimpleNewsDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("getByCategory/{category}")
    public List<SimpleNewsDTO> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    @GetMapping("get/{id}")
    public DetailedNewsDTO get(@PathVariable int id) {
        return service.get(id);
    }


    @PostMapping("add")
    @AdminAuthorized
    public String add(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @RequestBody CreateNews createNews) {
        return service.add(authHeader, createNews);
    }


    @DeleteMapping("delete/{id}")
    @AdminAuthorized
    public String delete(@PathVariable int id) {
        return service.delete(id);
    }

}
