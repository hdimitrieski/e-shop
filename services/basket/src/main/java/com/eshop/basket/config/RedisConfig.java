package com.eshop.basket.config;

import com.eshop.basket.model.CustomerBasket;
import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  /**
   * Client Options
   * Reject requests when redis is in disconnected state and
   * Redis will retry to connect automatically when redis server is down
   *
   * @return client options
   */
  @Bean
  public ClientOptions clientOptions() {
    return ClientOptions.builder()
        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
        .autoReconnect(true)
        .build();
  }

  /**
   * Redis template use redis data access
   *
   * @param redisConnectionFactory redisConnectionFactory
   * @return redisTemplate
   */
  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  @Primary
  public RedisTemplate<String, CustomerBasket> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, CustomerBasket> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setEnableTransactionSupport(true);
    return template;
  }
}
