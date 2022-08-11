# Order Processing Service

This is the core microservice. It's implemented following the Domain Driven Design approach and CQRS pattern. It Also 
uses Postgres to persist the orders.

To implement the CQRS pattern, we use [PipelinR](https://github.com/sizovs/PipelinR) command processing pipeline.

The code is organized in 3 core packages in the project:
#### api (Application layer)
Application layer is responsible for:
1. Handling domain events
2. Handling integration events
3. Handling commands
4. Handling queries
5. Publishing integration events

#### domain (Domain layer)
Domain layer is responsible for handling domain logic. It also dispatches domain events that are handled in the
application layer. Currently, we have two aggregates Order and Buyer. The aggregates expose their state as snapshot
by implementing the memento pattern.

#### infrastructure (Infrastructure layer)
Infrastructure layer is responsible for:
1. Mapping aggregate snapshots to entities and persists the entities 
2. Implementation of the repositories
3. Idempotency - each request that results in change of the order state is assigned request id. The request id is 
   persisted and, before the command is dispatched a check is performed to prevent handling of the same request twice.
   ex. In case the api gateway retries a request because of a timeout, the request will be handled only once.

Normal users who own token that contains "basket" scope have access to their orders, and they can create order drafts.
The admin users on the other hand, have access for all endpoints, including "/ship" and "/cancel".
If the JWT token doesn't contain "order-service" audience, it's considered invalid.

The Order Processing Service is subscribed for the following integration events:
1. **GracePeriodConfirmedIntegrationEvent**

    This event is published by the Order Grace Period Task in case of unsuccessful payment. 
    It will update the order status to "Awaiting Validation", and it will publish **OrderStatusChangedToAwaitingValidationIntegrationEvent**.

2. **OrderPaymentFailedIntegrationEvent**

    This event is published by the Payment Service.
    It will cancel the order - it's status will be changed to "Cancelled", and it will publish **OrderStatusChangedToCancelledIntegrationEvent**.

3. **OrderPaymentStatusChangedIntegrationEvent**

    This event is published by the Payment Service in case of successful payment.
    It will set the status of the order to "Paid", and it will publish **OrderStatusChangedToPaidIntegrationEvent**.

4. **OrderStockConfirmedIntegrationEventHandler**
    
    This event is published by the Catalog Service in case all ordered catalog items are available.
    It will set the status of the order to "Stock Confirmed", and it will publish **OrderStatusChangedToStockConfirmedIntegrationEvent**.

5. **OrderStockRejectedIntegrationEvent**

    This event is published by the Catalog Service in case some ordered catalog items are not available.
    It will cancel the order - it's status will be changed to "Cancelled", and it will publish **OrderStatusChangedToCancelledIntegrationEvent**.

6. **UserCheckoutAcceptedIntegrationEvent**

    This event is published by the Basket Service when the user does checkout.
    It will start the order process by creating new order with status "Submitted", and it will publish two domain events:
    **OrderStatusChangedToSubmittedIntegrationEvent** and **OrderStatusChangedToSubmittedIntegrationEvent**.

It also, publishes the following integration events:
1. **OrderStatusChangedToCancelledIntegrationEvent**

    This event will be published in case the admin cancel the order by calling "/cancel/${orderId}" endpoint.

2. **OrderStatusChangedToShippedIntegrationEvent**

   This event will be published in case the admin mark the order as shipped by calling "/ship/${orderId}" endpoint.

To update the database and publish integration event atomically, it uses the [Outbox pattern](https://microservices.io/patterns/data/transactional-outbox.html).

# Running the Order Processing Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -Dspring.profiles.active=dev -jar services/order-processing/build/libs/order-processing.jar

Optional profiles:
1. **dev** - to insert test data.
2. **elk** - to enable ELK logging.
3. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
4. **docker** - used when the service is run with docker.
