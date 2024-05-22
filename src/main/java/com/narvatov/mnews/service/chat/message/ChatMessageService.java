package com.narvatov.mnews.service.chat.message;

import com.narvatov.mnews.dao.ChatMessageDao;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.model.chat.message.ChatMessage;
import com.narvatov.mnews.model.chat.room.ChatRoom;
import com.narvatov.mnews.service.chat.room.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageDao dao;
    @Autowired
    private ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessageRequest chatMessageRequest) {
        ChatRoom chatRoom = chatRoomService.getById(chatMessageRequest);

        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(chatRoom.getId())
                .content(chatMessageRequest.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        return dao.save(chatMessage);
    }

}
