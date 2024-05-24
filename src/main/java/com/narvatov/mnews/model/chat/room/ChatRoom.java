package com.narvatov.mnews.model.chat.room;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Chat_Room", uniqueConstraints = @UniqueConstraint(columnNames={"first_user_id", "second_user_id"}))
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_user_id")
    private int firstUserId;
    @Column(name = "second_user_id")
    private int secondUserId;

    public ChatRoom(int firstUserId, int secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

}
