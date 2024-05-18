package com.narvatov.mnews.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.narvatov.mnews.dto.response.news.SimpleUserDTO;
import com.narvatov.mnews.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDTO {

    private int id;
    private String text;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
    private SimpleUserDTO author;

    public CommentDTO(Comment comment) {
        id = comment.getId();
        text = comment.getText();
        creationDate = comment.getCreationDate();
        author = new SimpleUserDTO(comment.getAuthor());
    }

}
