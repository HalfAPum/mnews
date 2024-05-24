package com.narvatov.mnews.dto.request.chat;

import lombok.Data;

@Data
public class ChatMessageNewsRequest {

    private int receiverId;
    private int newsId;

}
