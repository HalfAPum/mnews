package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.narvatov.mnews.model.auth.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue
    private int id;
    private String headline;
    @Column(columnDefinition="TEXT")
    private String content;
    @OneToOne
    private User author;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
    //define enum of categories
    private String category;
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<Comment> comments;
    //add image


    public News(String headline, String content, User author, String category) {
        this.headline = headline;
        this.content = content;
        creationDate = LocalDateTime.now();
        this.author = author;
        this.category = category;
    }
}