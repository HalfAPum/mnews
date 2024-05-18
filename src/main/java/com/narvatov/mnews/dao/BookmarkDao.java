package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.bookmark.BookmarkedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkDao extends JpaRepository<BookmarkedNews, Integer> {

}
