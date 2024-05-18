package com.narvatov.mnews.dto.response.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.narvatov.mnews.dto.response.comment.CommentDTO;
import com.narvatov.mnews.mapper.MapperKt;
import com.narvatov.mnews.model.News;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DetailedNewsDTO {

    private int id;
    private String headline;
    private String content;
    private SimpleUserDTO author;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
    private String category;
    private List<CommentDTO> comments;

    public DetailedNewsDTO(News news) {
        id = news.getId();
        headline = news.getHeadline();
        content = news.getContent();
        author = new SimpleUserDTO(news.getAuthor());
        creationDate = news.getCreationDate();
        category = news.getCategory();
        comments = MapperKt.mapToCommentDTO(news.getComments());
    }

}
