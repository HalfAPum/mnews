package com.narvatov.mnews.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatNotification {

    private String id;
    private String senderId;
    private String receiverId;

//    private int id;
//    @JsonProperty("sender_id")
//    private int senderId;
//    @JsonProperty("receiver_id")
//    private int receiverId;
    private String content;

}
