package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.chat.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageDao extends JpaRepository<ChatMessage, Integer> {
}
