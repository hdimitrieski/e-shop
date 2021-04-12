package com.eshop.signaler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  @Override
  protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
    messages
        .simpDestMatchers("/app/**").authenticated()
        .simpDestMatchers("/queue/**").authenticated() // TODO HD Add new scope .hasAuthority() order.notifications
        .anyMessage().authenticated();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }

}
