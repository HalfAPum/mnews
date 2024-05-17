package com.narvatov.mnews.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest {

    private int id;
    private String text;
    @JsonProperty("news_id")
    private int newsId;

}
