package com.eshop.ordering.backgroundtasks;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.api.application.commands.SetAwaitingValidationOrderStatusCommand;
import com.eshop.ordering.api.application.integrationevents.EventBus;
import com.eshop.ordering.backgroundtasks.events.GracePeriodConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final Pipeline pipeline;
//  private final EventBus eventBus;
  private final EntityManager entityManager;

  @Scheduled(fixedRate = 60000)
  public void execute() {
    System.out.printf("GracePeriodManagerService is starting at %s\n", dateFormat.format(new Date()));

    checkConfirmedGracePeriodOrders();
  }

  private void checkConfirmedGracePeriodOrders() {
    System.out.println("Checking confirmed grace period orders");
    var orderIds = getConfirmedGracePeriodOrders();
    for (var orderId : orderIds) {
      var confirmGracePeriodEvent = new GracePeriodConfirmedIntegrationEvent(orderId);

      System.out.printf("----- Publishing integration event: {%s}- ({%s})",
          confirmGracePeriodEvent.getId(), confirmGracePeriodEvent.getClass().getSimpleName());

      pipeline.send(new SetAwaitingValidationOrderStatusCommand(orderId));
//      eventBus.publish(confirmGracePeriodEvent);
    }
  }

  private List<Long> getConfirmedGracePeriodOrders() {
    var query = entityManager.createNativeQuery("""
          SELECT id
          FROM orders
          WHERE EXTRACT(EPOCH FROM (current_timestamp - order_date)) / 60 >= %d
                AND order_status_id = 1
        """.formatted(2));
    List<BigInteger> result = query.getResultList();
    return result.stream().map(BigInteger::longValue).collect(Collectors.toList());
  }
}
