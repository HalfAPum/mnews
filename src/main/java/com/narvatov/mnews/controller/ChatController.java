package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest1;
import com.narvatov.mnews.dto.response.chat.ChatNotification;
import com.narvatov.mnews.mapper.MapperKt;
import com.narvatov.mnews.model.chat.message.ChatMessage;
import com.narvatov.mnews.service.chat.message.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService service;

    @MessageMapping("chat")
    public void processMessage(@Payload ChatMessageRequest1 chatMessageRequest1) {
        ChatMessageRequest chatMessageRequest = new ChatMessageRequest(chatMessageRequest1);
        ChatMessage chatMessage = service.save(chatMessageRequest);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageRequest.getReceiverId()), "/queue/messages",
                new ChatNotification(chatMessage)
        );
    }

    @GetMapping("messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatNotification>> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
        return new ResponseEntity<>(MapperKt.mapToChatNotifications(service.getMessages(senderId, receiverId)), HttpStatus.OK);
    }

}
