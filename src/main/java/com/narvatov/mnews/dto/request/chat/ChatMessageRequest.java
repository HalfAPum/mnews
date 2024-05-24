package com.narvatov.mnews.dto.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMessageRequest {

    @JsonProperty("recipientId")
    private int receiverId;
    private String content;

}
