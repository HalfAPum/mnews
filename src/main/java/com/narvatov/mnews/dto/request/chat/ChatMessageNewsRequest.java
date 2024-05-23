package com.narvatov.mnews.dto.request.chat;

import lombok.Data;

@Data
public class ChatMessageNewsRequest {

    private int senderId;
    private int receiverId;
    private int newsId;

}
