package com.narvatov.mnews.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest {

    private int id;
    private String text;
    @JsonProperty("news_id")
    private int newsId;

}
