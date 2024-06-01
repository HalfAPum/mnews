package com.narvatov.mnews.interceptor;

import com.narvatov.mnews.service.auth.WebSocketInactivityTimerService;
import com.narvatov.mnews.service.auth.WebSocketSessionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class SessionTimeoutWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    private final WebSocketInactivityTimerService webSocketInactivityTimerService;
    private final WebSocketSessionService webSocketSessionService;


    public SessionTimeoutWebSocketHandlerDecorator(WebSocketHandler delegate, WebSocketInactivityTimerService webSocketInactivityTimerService, WebSocketSessionService webSocketSessionService) {
        super(delegate);
        this.webSocketInactivityTimerService = webSocketInactivityTimerService;
        this.webSocketSessionService = webSocketSessionService;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketInactivityTimerService.initializeTimer(session);
    }

    @Override
    public void handleMessage(@NotNull WebSocketSession session, @NotNull WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);

        Object payload = message.getPayload();

        boolean isHeartbeatMessage = payload instanceof String && ((String) payload).isBlank();

        if (isHeartbeatMessage) return;

        webSocketInactivityTimerService.resetTimer(session);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        webSocketSessionService.removeSession(session.getId());
        webSocketInactivityTimerService.removeTimer(session.getId());
    }

}
