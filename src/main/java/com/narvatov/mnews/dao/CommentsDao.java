package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsDao extends JpaRepository<Comment, Integer> {

}
