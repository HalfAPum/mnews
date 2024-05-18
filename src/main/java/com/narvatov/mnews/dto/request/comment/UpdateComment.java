package com.narvatov.mnews.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateComment {

    private int id;
    private String text;
    @JsonProperty("news_id")
    private int newsId;

}
