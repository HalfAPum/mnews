package com.narvatov.mnews.model.chat.room;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Chat_Room")
public class ChatRoom {

    @Column(unique = true)
    private int id;
    @EmbeddedId
    private ChatRoomId chatRoomId;

    public ChatRoom(int firstUserId, int secondUserId) {
        id = Math.abs(firstUserId - secondUserId) * firstUserId * secondUserId;
        chatRoomId = new ChatRoomId(firstUserId, secondUserId);
    }

}
