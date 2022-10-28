package com.eshop.ordering.config;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableKafka
public class KafkaConfig implements KafkaListenerConfigurer {

  private final KafkaProperties kafkaProperties;
  private final KafkaTopics topics;
  private final LocalValidatorFactoryBean validator;

  @Override
  public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
    registrar.setValidator(validator);
  }

  // Producer
  @Bean
  public DefaultErrorHandler errorHandler(
    DeadLetterPublishingRecoverer deadLetterPublishingRecoverer
  ) {
    final var loggingErrorHandler = new CommonLoggingErrorHandler();
//    loggingErrorHandler.
    return new DefaultErrorHandler(
      deadLetterPublishingRecoverer::accept,
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
    return new NewTopic(topics.getPaidOrders(), 1, (short) 1);
  }

  @Bean
  public NewTopic ordersWaitingForValidationTopic() {
    return new NewTopic(topics.getOrdersWaitingForValidation(), 1, (short) 1);
  }

  @Bean
  public NewTopic orderCheckoutsTopic() {
    return new NewTopic(topics.getOrderCheckouts(), 1, (short) 1);
  }

  @Bean
  public NewTopic orderStockConfirmedTopic() {
    return new NewTopic(topics.getOrderStockConfirmed(), 1, (short) 1);
  }

  @Bean
  public NewTopic orderStockRejectedTopic() {
    return new NewTopic(topics.getOrderStockRejected(), 1, (short) 1);
  }

  @Bean
  public NewTopic stockConfirmedTopic() {
    return new NewTopic(topics.getStockConfirmed(), 1, (short) 1);
  }

  @Bean
  public NewTopic paymentStatusTopic() {
    return new NewTopic(topics.getPaymentStatus(), 1, (short) 1);
  }

  @Bean
  public NewTopic gracePeriodConfirmedTopic() {
    return new NewTopic(topics.getGracePeriodConfirmed(), 1, (short) 1);
  }

  @Bean
  public NewTopic ordersTopic() {
    return new NewTopic(topics.getOrders(), 1, (short) 1);
  }

  @Bean
  public NewTopic cancelledOrdersTopic() {
    return new NewTopic(topics.getCancelledOrders(), 1, (short) 1);
  }

  @Bean
  public NewTopic shippedOrdersTopic() {
    return new NewTopic(topics.getShippedOrders(), 1, (short) 1);
  }

  @Bean
  public NewTopic submittedOrdersTopic() {
    return new NewTopic(topics.getSubmittedOrders(), 1, (short) 1);
  }

}
