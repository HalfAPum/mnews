package com.narvatov.mnews.interceptor;

import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.auth.JwtService;
import com.narvatov.mnews.service.auth.UserDetailsServiceImpl;
import com.narvatov.mnews.service.auth.WebSocketSessionService;
import com.narvatov.mnews.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TcpJwtAuthenticationInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;

        if (!StompCommand.CONNECT.equals(accessor.getCommand())) return message;

        String authHeader = accessor.getFirstNativeHeader(AuthUtils.AUTH_HEADER);

        assert authHeader != null;
        String jwt = AuthUtils.getJwt(authHeader);

        final String userEmail = jwtService.extractUserName(jwt);

        User userDetails = (User) userDetailsService.loadUserByUsername(userEmail);

        assert jwtService.isTokenValid(jwt, userDetails);
        log.debug("User - {}", userDetails);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        accessor.setUser(authToken);

        webSocketSessionService.saveSession(accessor.getSessionId(), userDetails);

        return message;
    }

}
