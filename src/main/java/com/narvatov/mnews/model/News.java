package com.narvatov.mnews.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public String headline;
    @Column(columnDefinition="TEXT")
    public String content;
    //make object?
    //consider multiple authors
    public String author;
    @JsonProperty("creation_date")
    public Date creationDate;
    //define enum of categories
    public String category;
    @JsonManagedReference
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    public List<Comment> comments;
    //add image

}
