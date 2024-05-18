package com.narvatov.mnews.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateComment {

    private String text;
    @JsonProperty("news_id")
    private int newsId;

}
