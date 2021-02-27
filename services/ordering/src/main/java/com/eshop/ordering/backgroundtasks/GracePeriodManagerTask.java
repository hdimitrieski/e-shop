package com.eshop.ordering.backgroundtasks;

import com.eshop.ordering.api.application.integrationevents.EventBus;
import com.eshop.ordering.backgroundtasks.events.GracePeriodConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GracePeriodManagerTask {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final EventBus eventBus;
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

      eventBus.publish(confirmGracePeriodEvent);
    }
  }

  private List<Long> getConfirmedGracePeriodOrders() {
    var query = entityManager.createNativeQuery("""
          SELECT id
          FROM orders
          WHERE (EXTRACT(EPOCH FROM current_timestamp) - EXTRACT(EPOCH FROM order_date))/3600 >= %d
                AND order_status_id = 1
        """.formatted(2));
    List<Long> results = query.getResultList();
    return results;
  }
}
