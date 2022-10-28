package com.eshop.basket.config;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.transaction.support.AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION;

@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;
  private final KafkaTopics topics;

  // Producer
  @Bean
  public DefaultErrorHandler errorHandler(
      DeadLetterPublishingRecoverer deadLetterPublishingRecoverer
  ) {
    return new DefaultErrorHandler(
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
    var producerFactory = new DefaultKafkaProducerFactory<String, IntegrationEvent>(producerConfigs());
    producerFactory.setTransactionIdPrefix(kafkaProperties.getProducer().getTransactionIdPrefix());
    return producerFactory;
  }

  @Bean
  public KafkaTemplate<String, IntegrationEvent> kafkaTemplate(ProducerFactory<String, IntegrationEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public KafkaTransactionManager<String, IntegrationEvent> kafkaTransactionManager(
      ProducerFactory<String, IntegrationEvent> producerFactory
  ) {
    var kafkaTransactionManager = new KafkaTransactionManager<>(producerFactory);
    kafkaTransactionManager.setTransactionSynchronization(SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
    return kafkaTransactionManager;
  }

  @Bean
  public AfterRollbackProcessor<String, IntegrationEvent> kafkaAfterRollbackProcessor(
      KafkaOperations<String, IntegrationEvent> kafkaTemplate) {
    var dlt = new DeadLetterPublishingRecoverer(kafkaTemplate);
    return new DefaultAfterRollbackProcessor<>(dlt);
  }

  // Consumer
  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  // Topics
  @Bean
  public NewTopic orderCheckoutsTopic() {
    return new NewTopic(topics.getOrderCheckouts(), 1, (short) 1);
  }

  @Bean
  public NewTopic ordersTopic() {
    return new NewTopic(topics.getOrders(), 1, (short) 1);
  }

  @Bean
  public NewTopic productPriceChangesTopic() {
    return new NewTopic(topics.getProductPriceChanges(), 1, (short) 1);
  }

  private ErrorHandlingDeserializer<Object> jsonDeserializer() {
    final var jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");
    return new ErrorHandlingDeserializer<>(jsonDeserializer);
  }

}
