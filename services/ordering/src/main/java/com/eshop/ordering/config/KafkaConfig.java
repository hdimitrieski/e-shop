package com.eshop.ordering.config;

import com.eshop.eventbus.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
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

  @Value("${spring.kafka.consumer.topic.paidOrders}")
  private String paidOrdersTopic;

  @Value("${spring.kafka.consumer.topic.ordersWaitingForValidation}")
  private String ordersWaitingForValidationTopic;

  @Value("${spring.kafka.consumer.topic.orderCheckouts}")
  private String orderCheckoutsTopic;

  @Value("${spring.kafka.consumer.topic.orderStockStatuses}")
  private String orderStockStatusesTopic;

  @Value("${spring.kafka.consumer.topic.stockConfirmed}")
  private String stockConfirmedTopic;

  @Value("${spring.kafka.consumer.topic.paymentStatus}")
  private String paymentStatusTopic;

  @Value("${spring.kafka.consumer.topic.gracePeriodConfirmed}")
  private String gracePeriodConfirmedTopic;

  @Value("${spring.kafka.consumer.topic.orders}")
  private String ordersTopic;

  @Value("${spring.kafka.consumer.topic.cancelledOrders}")
  private String cancelledOrdersTopic;

  @Value("${spring.kafka.consumer.topic.shippedOrders}")
  private String shippedOrdersTopic;

  @Value("${spring.kafka.consumer.topic.submittedOrders}")
  private String submittedOrdersTopic;

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
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  // Topics
  @Bean
  public NewTopic paidOrdersTopic() {
    return new NewTopic(paidOrdersTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic ordersWaitingForValidationTopic() {
    return new NewTopic(ordersWaitingForValidationTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic orderCheckoutsTopic() {
    return new NewTopic(orderCheckoutsTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic orderStockStatusesTopic() {
    return new NewTopic(orderStockStatusesTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic stockConfirmedTopic() {
    return new NewTopic(stockConfirmedTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic paymentStatusTopic() {
    return new NewTopic(paymentStatusTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic gracePeriodConfirmedTopic() {
    return new NewTopic(gracePeriodConfirmedTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic ordersTopic() {
    return new NewTopic(ordersTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic cancelledOrdersTopic() {
    return new NewTopic(cancelledOrdersTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic shippedOrdersTopic() {
    return new NewTopic(shippedOrdersTopic, 1, (short) 1);
  }

  @Bean
  public NewTopic submittedOrdersTopic() {
    return new NewTopic(submittedOrdersTopic, 1, (short) 1);
  }

  private ErrorHandlingDeserializer<Object> jsonDeserializer() {
    final var jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");
    return new ErrorHandlingDeserializer<>(jsonDeserializer);
  }

}
