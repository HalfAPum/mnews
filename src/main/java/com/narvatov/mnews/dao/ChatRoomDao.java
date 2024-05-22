package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.chat.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, Integer> {

    @Query(value = "SELECT * FROM Chat_Room" +
            " WHERE (first_user_id = ?1 AND second_user_id = ?2) " +
            "OR (first_user_id = ?2 AND second_user_id = ?1)" ,nativeQuery = true)
    Optional<ChatRoom> findByTwoUserIds(int firstUserId, int secondUserId);

}
