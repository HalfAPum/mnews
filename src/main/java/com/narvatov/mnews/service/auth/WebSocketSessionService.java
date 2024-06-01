package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.model.user.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {

    private final ConcurrentHashMap<String, User> map = new ConcurrentHashMap<>();

    public void saveSession(String sessionId, User user) {
        map.put(sessionId, user);

        Iterator<String> it = map.keys().asIterator();
        System.out.print("FUCK SESSION LIST AFTER ADD: ");
        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();
    }

    public void removeSession(String sessionId) {
        map.remove(sessionId);
        Iterator<String> it = map.keys().asIterator();

        System.out.print("FUCK SESSION LIST AFTER REMOVE: ");
        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();
    }

    public int getUserId(String sessionId) {
        return map.get(sessionId).getId();
    }

}
