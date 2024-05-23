package com.narvatov.mnews.service.chat.room;

import com.narvatov.mnews.dao.ChatRoomDao;
import com.narvatov.mnews.model.chat.room.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomDao dao;


    public ChatRoom getByIds(int senderId, int receiverId) {
        Optional<ChatRoom> chatRoom = dao.findByTwoUserIds(senderId, receiverId);

        return chatRoom.orElseGet(() -> createChatRoom(senderId, receiverId));
    }

    private ChatRoom createChatRoom(int senderId, int receiverId) {
        ChatRoom createChatRoom = new ChatRoom(senderId, receiverId);

        return dao.save(createChatRoom);
    }

}
