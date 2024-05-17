package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.narvatov.mnews.dto.request.CommentRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private LocalDateTime creationDate;
    @JsonBackReference
    @ManyToOne
    private News news;

    public Comment(CommentRequest commentRequest) {
        id = commentRequest.getId();
        text = commentRequest.getText();
        //TODO add update date and don't change creationDate on update
        creationDate = LocalDateTime.now();
        news = new News();
        news.setId(commentRequest.getNewsId());
    }

}
