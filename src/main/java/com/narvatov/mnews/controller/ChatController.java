package com.narvatov.mnews.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.narvatov.mnews.dto.request.chat.ChatMessageNewsRequest;
import com.narvatov.mnews.dto.request.chat.ChatMessageRequest;
import com.narvatov.mnews.dto.response.chat.ChatNotification;
import com.narvatov.mnews.mapper.MapperKt;
import com.narvatov.mnews.model.auth.annotation.Authenticated;
import com.narvatov.mnews.model.chat.message.ChatMessage;
import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.auth.UserService;
import com.narvatov.mnews.service.auth.WebSocketSessionService;
import com.narvatov.mnews.service.chat.message.ChatMessageService;
import com.narvatov.mnews.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/chat")
public class ChatController {

    @Autowired
    private WebSocketSessionService webSocketSessionService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService service;
    @Autowired
    private UserService userService;

    @MessageMapping("chat/text")
    public void processMessage(SimpMessageHeaderAccessor headerAccessor, @Payload ChatMessageRequest chatMessageRequest) {
        int userId = webSocketSessionService.getUserId(headerAccessor);

        ChatMessage chatMessage = service.save(userId, chatMessageRequest);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageRequest.getReceiverId()), "/queue/messages",
                new ChatNotification(chatMessage)
        );
    }

    @MessageMapping("chat/news")
    public void processMessage(SimpMessageHeaderAccessor headerAccessor, @Payload ChatMessageNewsRequest chatMessageNewsRequest) throws JsonProcessingException {
        int userId = webSocketSessionService.getUserId(headerAccessor);

        ChatMessage chatMessage = service.save(userId, chatMessageNewsRequest);

        ChatNotification chatNotification = new ChatNotification(chatMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(userId), "/queue/messages",
                chatNotification
        );

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageNewsRequest.getReceiverId()), "/queue/messages",
                chatNotification
        );
    }

    @GetMapping("messages/{receiverId}")
    @Authenticated
    public ResponseEntity<List<ChatNotification>> getMessages(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader, @PathVariable int receiverId) {
        User user = userService.extractUserFromAuthHeader(authHeader);
        return new ResponseEntity<>(MapperKt.mapToChatNotifications(service.getMessages(user.getId(), receiverId)), HttpStatus.OK);
    }

    //This endpoint is actually redundant but since I test chat behavior without login page
    //and no information about user id we need to know user id on client side from jwt token
    @GetMapping("getUserId")
    @Authenticated
    public int getUserId(@RequestHeader(AuthUtils.AUTH_HEADER) String authHeader) {
        return userService.extractUserFromAuthHeader(authHeader).getId();
    }

}
