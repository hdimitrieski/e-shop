package com.eshop.basket.config;

import com.eshop.eventbus.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  @Value("${spring.kafka.consumer.topic.orderCheckouts}")
  private String orderCheckoutsTopic;

  @Value("${spring.kafka.consumer.topic.orders}")
  private String ordersTopic;

  @Value("${spring.kafka.consumer.topic.productPriceChanges}")
  private String productPriceChangesTopic;

  // Producer
  @Bean
  public SeekToCurrentErrorHandler errorHandler(
      DeadLetterPublishingRecoverer deadLetterPublishingRecoverer
  ) {
    return new SeekToCurrentErrorHandler(
        deadLetterPublishingRecoverer,
        new FixedBackOff(1000L, 2)
    );
  }

  /**
   * Configure the {@link DeadLetterPublishingRecoverer} to publish poison pill bytes to a dead letter topic:
   * "topic-name.DLT".
   */
  @Bean
  public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaOperations<String, IntegrationEvent> template) {
    return new DeadLetterPublishingRecoverer(template);
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    var props = new HashMap<>(kafkaProperties.buildProducerProperties());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return props;
  }

  @Bean
  public ProducerFactory<String, IntegrationEvent> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, IntegrationEvent> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  // Consumer
  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        kafkaProperties.buildConsumerProperties(),
        new StringDeserializer(),
        jsonDeserializer()
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  // Topics
  @Bean
  public NewTopic orderCheckoutsTopic() {
    return new NewTopic(orderCheckoutsTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic ordersTopic() {
    return new NewTopic(ordersTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic productPriceChangesTopic() {
    return new NewTopic(productPriceChangesTopic, 1, (short) 1);
  }

  private ErrorHandlingDeserializer<Object> jsonDeserializer() {
    final var jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");
    return new ErrorHandlingDeserializer<>(jsonDeserializer);
  }

}
