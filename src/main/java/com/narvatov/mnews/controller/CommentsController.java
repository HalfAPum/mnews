package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.CommentRequest;
import com.narvatov.mnews.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentsController {

    private final CommentsService service;

    @PostMapping("add")
    public String add(@RequestBody CommentRequest commentRequest) {
        return service.add(commentRequest);
    }

    @PutMapping("update")
    public String update(@RequestBody CommentRequest commentRequest) {
        return service.update(commentRequest);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

}
