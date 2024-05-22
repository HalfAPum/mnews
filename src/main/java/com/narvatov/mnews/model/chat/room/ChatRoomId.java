package com.narvatov.mnews.model.chat.room;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomId {

    @Column(name = "first_user_id")
    private int firstUserId;
    @Column(name = "second_user_id")
    private int secondUserId;

}
