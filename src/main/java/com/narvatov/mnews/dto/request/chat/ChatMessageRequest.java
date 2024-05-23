package com.narvatov.mnews.dto.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMessageRequest {

    @JsonProperty("sender_id")
    private int senderId;
    @JsonProperty("receiver_id")
    private int receiverId;
    private String content;

    public ChatMessageRequest(ChatMessageRequestRaw req) {
        senderId = Integer.parseInt(req.getSenderId());
        receiverId = Integer.parseInt(req.getRecipientId());
        content = req.getContent();
    }
}
