package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao extends JpaRepository<News, Integer> {

    @Query(value = "SELECT * FROM News ORDER BY creation_date DESC", nativeQuery = true)
    List<News> findAllNoCommentsSortedByDate();

    @Query(value = "SELECT * FROM News WHERE category = :category ORDER BY creation_date DESC", nativeQuery = true)
    List<News> findByCategory(String category);

}
