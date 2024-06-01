package com.narvatov.mnews.service.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketInactivityTimerService {

    //5 min inactivity
    private static final long INACTIVITY_TIMEOUT = 1_000 * 30;

    private final ConcurrentHashMap<String, Timer> map = new ConcurrentHashMap<>();

    public void initializeTimer(WebSocketSession session) {
        Timer timer = new Timer();
        timer.schedule(getInactivityTask(session), INACTIVITY_TIMEOUT);

        map.put(session.getId(), timer);
    }

    private TimerTask getInactivityTask(WebSocketSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    if (session.isOpen()) {
                        session.close();
                    }
                    else map.remove(session.getId());
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        };
    }

    public void resetTimer(WebSocketSession session) {
        Timer timer = map.get(session.getId());

        if (timer != null) timer.cancel();

        initializeTimer(session);
    }

    public void removeTimer(String sessionId) {
        map.remove(sessionId);
    }

}
