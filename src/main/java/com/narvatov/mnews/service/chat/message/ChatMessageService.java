package com.narvatov.mnews.service.chat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.narvatov.mnews.dao.ChatMessageDao;
import com.narvatov.mnews.dto.request.chat.ChatMessageNewsRequest;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO;
import com.narvatov.mnews.model.chat.message.ChatMessage;
import com.narvatov.mnews.model.chat.room.ChatRoom;
import com.narvatov.mnews.service.NewsService;
import com.narvatov.mnews.service.chat.room.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageDao dao;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private NewsService newsService;

    public ChatMessage save(int senderId, ChatMessageRequest chatMessageRequest) {
        ChatRoom chatRoom = chatRoomService.getByIds(senderId, chatMessageRequest.getReceiverId());

        return save(chatRoom.getId(), senderId, chatMessageRequest.getContent());
    }

    public ChatMessage save(int senderId, ChatMessageNewsRequest chatMessageNewsRequest) throws JsonProcessingException {
        ChatRoom chatRoom = chatRoomService.getByIds(senderId, chatMessageNewsRequest.getReceiverId());

        SimpleNewsDTO news = newsService.getSimpleNewsById(chatMessageNewsRequest.getNewsId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String newsJson = ow.writeValueAsString(news);

        return save(chatRoom.getId(), senderId, newsJson);
    }

    private ChatMessage save(int chatId, int senderId, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(chatId)
                .senderId(senderId)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        return dao.save(chatMessage);
    }


    public List<ChatMessage> getMessages(int senderId, int receiverId) {
        ChatRoom chatRoom = chatRoomService.getByIds(senderId, receiverId);

        return dao.findByRoomId(chatRoom.getId());
    }

}
