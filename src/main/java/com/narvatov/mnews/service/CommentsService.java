package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.CommentsDao;
import com.narvatov.mnews.model.Comment;
import com.narvatov.mnews.dto.request.CommentRequest;
import com.narvatov.mnews.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    @Autowired
    private CommentsDao dao;

    public String add(CommentRequest commentRequest) {
        dao.save(new Comment(commentRequest));

        return ServerResponse.getCreatedMessage(commentRequest);
    }

    public String update(CommentRequest commentRequest) {
        dao.save(new Comment(commentRequest));

        return ServerResponse.getUpdatedMessage(commentRequest);
    }


    public String delete(int id) {
        dao.deleteById(id);

        return ServerResponse.getDeletedMessage(id);
    }

}
