# e-shop

# Integration events

## Basket
### Publishes
1. UserCheckoutAcceptedIntegrationEvent (handled by Ordering)

### Handles
1. OrderStartedIntegrationEvent (from Ordering) (triggers -nothing-)
2. ProductPriceChangedIntegrationEvent (from Catalog) (triggers -nothing-)

## Catalog
### Publishes
1. ProductPriceChangedIntegrationEvent (handled by Basket)
2. OrderStockConfirmedIntegrationEvent (handled by Ordering) (triggered by 1)
3. OrderStockRejectedIntegrationEvent (handled by Ordering) (triggered by 1)

### Handles
1. OrderStatusChangedToAwaitingValidationIntegrationEvent (from Ordering) (triggers 2 | 3)
2. OrderStatusChangedToPaidIntegrationEvent (from Ordering) (triggers -nothing-)

## Payment
### Publishes
1. OrderPaymentFailedIntegrationEvent (handled by Ordering) (triggered by 1)
2. OrderPaymentSucceededIntegrationEvent (handled by Ordering) (triggered by 1)

### Handles
1. OrderStatusChangedToStockConfirmedIntegrationEvent (from Ordering) (triggers 1 | 2)

## Ordering
### Publishes
1. OrderStartedIntegrationEvent (handled by Basket) (triggered by 6)
2. OrderStatusChangedToAwaitingValidationIntegrationEvent (handled by Catalog) (triggered by 1)
3. OrderStatusChangedToPaidIntegrationEvent (handled by Catalog) (triggered by 3)
4. OrderStatusChangedToCancelledIntegrationEvent (no handlers) (triggered by 2)
5. OrderStatusChangedToShippedIntegrationEvent (no handlers) (triggered by "/cancel" endpoint)
6. OrderStatusChangedToSubmittedIntegrationEvent (no handlers) (triggered by 6)
7. OrderStatusChangedToStockConfirmedIntegrationEvent (handled by Ordering) (triggered by 4)
8. GracePeriodConfirmedIntegrationEvent (handled by Ordering) (triggered by background task after OrderStartedIntegrationEvent)

### Handles
1. GracePeriodConfirmedIntegrationEvent (from Ordering - background task) (triggers 2)
2. OrderPaymentFailedIntegrationEvent (from Payment) (triggers 4)
3. OrderPaymentSucceededIntegrationEvent (from Payment) (triggers 3)
4. OrderStockConfirmedIntegrationEvent (from Catalog) (triggers 7)
5. OrderStockRejectedIntegrationEvent (from Catalog) (triggers -nothing-)
6. UserCheckoutAcceptedIntegrationEvent (from Basket) (triggers 1, 6)

# Topics and events
1. order-checkouts
    - UserCheckoutAcceptedIntegrationEvent
    
2. payment-status
    - OrderPaymentFailedIntegrationEvent
    - OrderPaymentSucceededIntegrationEvent
    
3. product-price-changes
    - ProductPriceChangedIntegrationEvent
    
4. order-stock-statuses
    - OrderStockRejectedIntegrationEvent
    - OrderStockConfirmedIntegrationEvent
    
5. orders
    - OrderStartedIntegrationEvent
    
6. orders-waiting-for-validation
    - OrderStatusChangedToAwaitingValidationIntegrationEvent
    
7. paid-orders
    - OrderStatusChangedToPaidIntegrationEvent

8. grace-period-confirmed
    - GracePeriodConfirmedIntegrationEvent

6. stock-confirmed
    - OrderStatusChangedToStockConfirmedIntegrationEvent

# Grace period management
Create spring.cloud.task for the grace period management (GracePeriodManagerTask). 
The task is like the amazon lambda - it starts, executes a job and exits. Also, it can be connected
with a database, so it can write when it's starting, execution result, etc.
Also, this should be a separate module under ordering.

Also, I can have tasks that are subscribed to a message bus and do something
only when there is a message related to the task.

OR I can use spring.cloud.dataflow to schedule tasks.

OR before I start using spring.cloud.task i can just create a new module that will 
contain spring application which will schedule the task. ex. https://spring.io/guides/gs/scheduling-tasks/.


See:
- https://app.pluralsight.com/course-player?clipId=a4627744-c799-47db-8517-4f76c7ca74d9
- https://github.com/RawSanj/spring-cloud-task-as-k8s-cronjob

# Data pipeline
See what's spring cloud data flow. 

# Spring cloud stream
Use spring cloud stream instead of using Kafka directly. Use kafka only
in the analytics service because there we will need to combine multiple streams.
Add headers in the messages and dispatch the message to different listeners based 
on the header's value, ex. priority order header and have two listeners one for priority
and one for normal orders.
Also use processors, basically a processor is consumer and producer at the same time.
And also use consumer groups. Consumer groups are used to avoid handling of a message by
all consumers in case we have multiple instances of the service that handles that particular message running.
Also consumer groups are durable, meaning that the actual queue won't be removed in case the service goes down,
so when it comes back online, it could take all messages that have been queued during its downtime.

Partitions: a way to have dedicated instaces for handling specific messages. The messages can be split up
based on some property, say we have message {id: x} and 2 partitions
p1: handles {id: 1}, {id: 3}, ...
p2: {id: 2}, {id: 4}, ...


# Spring data flow (microservices orchestration)

# Auth
In case the token expires while it's being used between services, I need to
refresh it. (check the course on pluralsight)


- Check antMachers vs mvcMachers, do not pass the acess token for catalog routes,
if its passed the request is denied - this shouldnt happen
