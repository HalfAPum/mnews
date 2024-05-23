package com.narvatov.mnews.dto.request.chat;

import lombok.Data;

@Data
public class ChatMessageRequestRaw {

    private String senderId;
    private String recipientId;
    private String content;

}
