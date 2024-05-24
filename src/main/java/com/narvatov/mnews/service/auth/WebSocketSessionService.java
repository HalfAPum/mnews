package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.model.user.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {

    private final ConcurrentHashMap<String, User> map = new ConcurrentHashMap<>();

    public void saveSession(String sessionId, User user) {
        map.put(sessionId, user);
    }

    public int getUserId(SimpMessageHeaderAccessor headerAccessor) {
        return map.get(headerAccessor.getSessionId()).getId();
    }

}
