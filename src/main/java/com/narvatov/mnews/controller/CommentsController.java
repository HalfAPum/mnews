package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.comment.CreateComment;
import com.narvatov.mnews.dto.request.comment.UpdateComment;
import com.narvatov.mnews.model.auth.annotation.Authenticated;
import com.narvatov.mnews.service.CommentsService;
import com.narvatov.mnews.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
public class CommentsController {

    @Autowired
    private CommentsService service;

    @PostMapping("add")
    @Authenticated
    public String add(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @RequestBody CreateComment createComment) {
        return service.add(authHeader, createComment);
    }

    @PutMapping("update")
    @Authenticated
    public String update(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @RequestBody UpdateComment updateComment) {
        return service.update(authHeader, updateComment);
    }

    @DeleteMapping("delete/{id}")
    @Authenticated
    public String delete(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @PathVariable("id") int id) {
        return service.delete(authHeader, id);
    }

}
