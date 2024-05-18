package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.narvatov.mnews.dto.request.comment.CreateComment;
import com.narvatov.mnews.dto.request.comment.UpdateComment;
import com.narvatov.mnews.model.auth.User;
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
    @OneToOne
    private User author;
    @JsonBackReference
    @ManyToOne
    private News news;

    public Comment(CreateComment createComment, User author) {
        text = createComment.getText();
        creationDate = LocalDateTime.now();
        this.author = author;

        news = new News();
        news.setId(createComment.getNewsId());
    }

    public Comment(UpdateComment commentRequest, User author) {
        id = commentRequest.getId();
        text = commentRequest.getText();
        //TODO add update date and don't change creationDate on update
        creationDate = LocalDateTime.now();
        this.author = author;

        news = new News();
        news.setId(commentRequest.getNewsId());
    }

}
