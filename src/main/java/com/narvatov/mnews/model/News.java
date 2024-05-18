package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public int id;
    public String headline;
    @Column(columnDefinition="TEXT")
    public String content;
    //make object?
    //consider multiple authors
    public String author;
    @JsonProperty("creation_date")
    public LocalDateTime creationDate;
    //define enum of categories
    public String category;
    @JsonManagedReference
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    public List<Comment> comments;
    //add image

}
