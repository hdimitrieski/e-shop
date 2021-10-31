package com.eshop.catalog.config;

import com.mongodb.client.MongoClient;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  /**
   * Configures command bus.
   */
  @Bean
  public CommandBus commandBus(TransactionManager txManager) {
    SimpleCommandBus commandBus = SimpleCommandBus.builder()
        .transactionManager(txManager)
        .build();
    commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());

    return commandBus;
  }

  /**
   * Configures command gateway.
   */
  @Bean
  public CommandGateway commandGateway(CommandBus commandBus) {
    return DefaultCommandGateway.builder().commandBus(commandBus).build();
  }

  /**
   * Configures Mongo embedded event store.
   */
  @Bean
  public EventStore eventStore(MongoClient client, Serializer serializer) {
    return EmbeddedEventStore.builder()
        .storageEngine(eventStorageEngine(client, serializer)).build();
  }

  /**
   * Configures Mongo based Event Storage Engine.
   */
  private EventStorageEngine eventStorageEngine(MongoClient client, Serializer serializer) {
    return MongoEventStorageEngine.builder()
        .eventSerializer(serializer)
        .mongoTemplate(DefaultMongoTemplate.builder()
            .mongoDatabase(client)
            .build())
        .build();
  }

  /**
   * Creates Mongo based Token Store.
   */
  @Bean
  public TokenStore tokenStore(MongoClient client, Serializer serializer) {
    return MongoTokenStore.builder()
        .mongoTemplate(DefaultMongoTemplate.builder()
            .mongoDatabase(client)
            .build())
        .serializer(serializer)
        .build();
  }

  /**
   * Configures a snapshot trigger to create a Snapshot every 5 events.
   * 5 is an arbitrary number used only for testing purposes just to show how the snapshots are stored on Mongo as well.
   */
//  @Bean
//  public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
//    return EventCountSnapshotTriggerDefinition(configuration.snapshotter(), 5);
//  }

}
