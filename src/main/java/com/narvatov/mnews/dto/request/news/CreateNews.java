package com.narvatov.mnews.dto.request.news;

import com.narvatov.mnews.model.News;
import com.narvatov.mnews.model.auth.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateNews {

    private String headline;
    private String content;
    private String category;

    public News getNews(User author) {
        return new News(headline, content, author, category);
    }

}
