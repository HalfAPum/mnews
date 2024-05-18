package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.CommentRequest;
import com.narvatov.mnews.model.auth.annotation.Authenticated;
import com.narvatov.mnews.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
public class CommentsController {

    @Autowired
    private CommentsService service;

    @PostMapping("add")
    @Authenticated
    public String add(@RequestBody CommentRequest commentRequest) {
        return service.add(commentRequest);
    }

    @PutMapping("update")
    @Authenticated
    public String update(@RequestBody CommentRequest commentRequest) {
        return service.update(commentRequest);
    }

    @DeleteMapping("delete/{id}")
    @Authenticated
    public String delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

}
