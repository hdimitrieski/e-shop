package com.eshop.signaler.config;

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
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;
  private final KafkaTopics topics;

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
    var converter = new StringJsonMessageConverter();
//    var mapper = new DefaultJackson2JavaTypeMapper();
//    mapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//    mapper.addTrustedPackages("*");
//    Map<String, Class<?>> mappings = new HashMap<>();
//    mappings.put("foo", Foo2.class);
//    mappings.put("bar", Bar2.class);
//    typeMapper.setIdClassMapping(mappings);
//    converter.setTypeMapper(mapper);

    return converter;
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
  public NewTopic stockConfirmedTopic() {
    return new NewTopic(topics.getStockConfirmed(), 1, (short) 1);
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
