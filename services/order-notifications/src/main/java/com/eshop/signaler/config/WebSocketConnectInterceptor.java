package com.eshop.signaler.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WebSocketConnectInterceptor implements ChannelInterceptor {
  private final JwtDecoder jwtDecoderByIssuerUri;
  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
      List<String> authorization = accessor.getNativeHeader("Authorization");
      String accessToken = authorization.get(0).split(" ")[1];
      var jwt = jwtDecoderByIssuerUri.decode(accessToken);
      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
      converter.setPrincipalClaimName(userNameAttribute);
      Authentication authentication = converter.convert(jwt);
      accessor.setUser(authentication);
    }

    return message;
  }
}
