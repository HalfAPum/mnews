package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.narvatov.mnews.model.dto.request.CommentRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private String text;
    private Date creationDate;
    @JsonBackReference
    @ManyToOne
    private News news;

    public Comment() {}

    public Comment(CommentRequest commentRequest) {
        id = commentRequest.getId();
        text = commentRequest.getText();
        creationDate = new Date();
        news = new News();
        news.setId(commentRequest.getNewsId());
    }

}
