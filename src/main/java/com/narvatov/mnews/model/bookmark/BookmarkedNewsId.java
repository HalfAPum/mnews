package com.narvatov.mnews.model.bookmark;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkedNewsId {

    @Column(name = "user_id")
    private int userId;
    @Column(name = "news_id")
    private int newsId;

}
