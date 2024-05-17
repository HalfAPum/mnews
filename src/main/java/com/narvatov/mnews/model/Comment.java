package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.narvatov.mnews.model.dto.request.CommentRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private String text;
    private LocalDateTime creationDate;
    @JsonBackReference
    @ManyToOne
    private News news;

    public Comment() {}

    public Comment(CommentRequest commentRequest) {
        id = commentRequest.getId();
        text = commentRequest.getText();
        //TODO add update date and don't change creationDate on update
        creationDate = LocalDateTime.now();
        news = new News();
        news.setId(commentRequest.getNewsId());
    }

}
