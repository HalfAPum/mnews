package com.narvatov.mnews.dto.response.chat;

import com.narvatov.mnews.model.chat.message.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatNotification {

    private String id;
    private String senderId;
    private String content;

    public ChatNotification(ChatMessage chatMessage) {
        id = String.valueOf(chatMessage.getId());
        senderId = String.valueOf(chatMessage.getSenderId());
        content = chatMessage.getContent();
    }

}
