package com.narvatov.mnews.service.chat.room;

import com.narvatov.mnews.dao.ChatRoomDao;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.model.chat.room.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomDao dao;


    public ChatRoom getById(ChatMessageRequest chatMessageRequest) {
        Optional<ChatRoom> chatRoom = dao.findByTwoUserIds(chatMessageRequest.getSenderId(), chatMessageRequest.getReceiverId());

        return chatRoom.orElseGet(() -> createChatRoom(chatMessageRequest));
    }

    private ChatRoom createChatRoom(ChatMessageRequest chatMessageRequest) {
        ChatRoom createChatRoom = new ChatRoom(chatMessageRequest.getSenderId(), chatMessageRequest.getReceiverId());

        return dao.save(createChatRoom);
    }

}
