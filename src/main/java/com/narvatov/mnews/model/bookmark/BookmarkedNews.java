package com.narvatov.mnews.model.bookmark;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Bookmarked_News")
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkedNews {

    @EmbeddedId
    private BookmarkedNewsId bookmarkedNewsId;

    public BookmarkedNews(int userId, int newsId) {
        bookmarkedNewsId = new BookmarkedNewsId(userId, newsId);
    }

}
