package com.eshop.basket;

import com.eshop.basket.model.CustomerBasket;
import io.lettuce.core.ClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  /**
   * Redis configuration
   *
   * @return redisStandaloneConfiguration
   */
  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
    redisStandaloneConfiguration.setPassword("pass");
    return redisStandaloneConfiguration;
  }

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
   * Create a LettuceConnection with redis configurations and client options
   *
   * @param redisStandaloneConfiguration redisStandaloneConfiguration
   * @return RedisConnectionFactory
   */
  @Bean
  public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {

    LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
        .clientOptions(clientOptions()).build();

    return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
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
    return template;
  }
}
