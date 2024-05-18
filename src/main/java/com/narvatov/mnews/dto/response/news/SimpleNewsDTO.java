package com.narvatov.mnews.dto.response.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.narvatov.mnews.model.News;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleNewsDTO {

    private int id;
    private String headline;
    private String content;
    private SimpleUserDTO author;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
    private String category;

    public SimpleNewsDTO(News news) {
        id = news.getId();
        headline = news.getHeadline();
        content = news.getContent();
        author = new SimpleUserDTO(news.getAuthor());
        creationDate = news.getCreationDate();
        category = news.getCategory();
    }

}
