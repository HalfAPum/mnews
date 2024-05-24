package com.narvatov.mnews.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.narvatov.mnews.service.auth.JwtService;
import com.narvatov.mnews.service.auth.UserDetailsServiceImpl;
import com.narvatov.mnews.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix(("/user"));
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws");
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(APPLICATION_JSON);

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);

        messageConverters.add(converter);

        return false;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                assert accessor != null;

                if (!StompCommand.CONNECT.equals(accessor.getCommand())) return message;

                String authHeader = accessor.getFirstNativeHeader(AuthUtils.AUTH_HEADER);

                assert authHeader != null;
                String jwt = AuthUtils.getJwt(authHeader);

                final String userEmail = jwtService.extractUserName(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                assert jwtService.isTokenValid(jwt, userDetails);
                log.debug("User - {}", userDetails);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

                accessor.setUser(authToken);

                return message;
            }
        });
    }
}
