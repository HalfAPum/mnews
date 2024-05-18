package com.narvatov.mnews.dto.request.bookmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookmark {

    @JsonProperty("news_id")
    private int newsId;

}
