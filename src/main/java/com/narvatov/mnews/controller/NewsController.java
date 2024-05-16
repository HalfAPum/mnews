package com.narvatov.mnews.controller;

import com.narvatov.mnews.model.News;
import com.narvatov.mnews.model.dto.response.SimpleNewsDTO;
import com.narvatov.mnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService service;


    //Provide paging
    @GetMapping("getAll")
    public List<SimpleNewsDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("getByCategory/{category}")
    public List<News> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    @GetMapping("get/{id}")
    public News get(@PathVariable int id) {
        return service.get(id);
    }


    @PostMapping("add")
    public String add(@RequestBody News news) {
        return service.add(news);
    }


    @PutMapping("update")
    public String update(@RequestBody News news) {
        return service.update(news);
    }


    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return service.delete(id);
    }

}
