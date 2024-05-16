package com.narvatov.mnews.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;
    public String headline;
    @Column(columnDefinition="TEXT")
    public String content;
    //make object?
    //consider multiple authors
    public String author;
    public Date date;
    //define enum of categories
    public String category;
    //add image
    //add comments

}
