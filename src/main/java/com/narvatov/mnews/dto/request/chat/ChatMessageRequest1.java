package com.narvatov.mnews.dto.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMessageRequest1 {

    private String senderId;
    private String recipientId;
    private String content;

}
