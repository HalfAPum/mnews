package com.narvatov.mnews.controller;

import com.narvatov.mnews.dao.UserDao;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest1;
import com.narvatov.mnews.dto.response.chat.ChatNotification;
import com.narvatov.mnews.model.chat.message.ChatMessage;
import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.chat.message.ChatMessageService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService service;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest1 chatMessageRequest1) {
        ChatMessageRequest chatMessageRequest = new ChatMessageRequest(chatMessageRequest1);
        ChatMessage chatMessage = service.save(chatMessageRequest);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageRequest.getReceiverId()), "/queue/messages",
                ChatNotification.builder()
                        .id(String.valueOf(chatMessage.getId()))
                        .senderId(String.valueOf(chatMessageRequest.getSenderId()))
                        .receiverId(String.valueOf(chatMessageRequest.getReceiverId()))
                        .content(chatMessage.getContent())
                        .build()
        );
    }

    @Autowired
    private UserDao dao;

    @Data
    @Builder
    static class UDTO {
        private String id;
        private String nickName;
        private String fullName;
    }

    @GetMapping("users")
    public List<UDTO> getAll() {
        return dao.findAll().stream().map(user -> UDTO.builder().id(String.valueOf(user.getId())).nickName(String.valueOf(user.getId())).fullName(String.valueOf(user.getId())).build()).collect(Collectors.toList());
    }

}
