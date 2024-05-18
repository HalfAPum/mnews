package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkedNewsDao extends JpaRepository<News, Integer> {

    @Query(value = "SELECT * FROM Bookmarked_News " +
            "INNER JOIN News ON News.id=Bookmarked_News.news_id " +
            "WHERE Bookmarked_News.user_id = ?1", nativeQuery = true)
    List<News> findAllBookmarkedNews(int userId);

}
