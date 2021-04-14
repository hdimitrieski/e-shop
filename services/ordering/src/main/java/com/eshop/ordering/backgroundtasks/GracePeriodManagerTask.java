package com.eshop.ordering.backgroundtasks;

import com.eshop.ordering.api.application.integrationevents.EventBus;
import com.eshop.ordering.api.application.integrationevents.eventhandling.GracePeriodConfirmedIntegrationEventHandler;
import com.eshop.ordering.backgroundtasks.events.GracePeriodConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO HD
 * This task should exist in a separate application. Until we add new app for it, we will dispatch commands directly
 * instead of publishing an event.
 */
@Component
@RequiredArgsConstructor
public class GracePeriodManagerTask {
  private static final Logger logger = LoggerFactory.getLogger(GracePeriodManagerTask.class);
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final EventBus eventBus;
  private final EntityManager entityManager;
  @Value("${spring.kafka.consumer.topic.gracePeriodConfirmed}")
  private String gracePeriodConfirmedTopic;

  @Scheduled(fixedRate = 60000)
  public void execute() {
    logger.info("GracePeriodManagerService is starting at {}", dateFormat.format(new Date()));

    checkConfirmedGracePeriodOrders();
  }

  private void checkConfirmedGracePeriodOrders() {
    logger.info("Checking confirmed grace period orders");
    var orderIds = getConfirmedGracePeriodOrders();
    for (var orderId : orderIds) {
      var confirmGracePeriodEvent = new GracePeriodConfirmedIntegrationEvent(orderId);
      eventBus.publish(gracePeriodConfirmedTopic, confirmGracePeriodEvent);
    }
  }

  private List<Long> getConfirmedGracePeriodOrders() {
    var query = entityManager.createNativeQuery("""
          SELECT id
          FROM orders
          WHERE EXTRACT(EPOCH FROM (current_timestamp - order_date)) / 60 >= %d
                AND order_status_id = 1
        """.formatted(1));
    List<BigInteger> result = query.getResultList();
    return result.stream().map(BigInteger::longValue).collect(Collectors.toList());
  }
}
