package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao extends JpaRepository<News, Integer> {

    List<News> findByCategory(String category, Sort sort);

}
