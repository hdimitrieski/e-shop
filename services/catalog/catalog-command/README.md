# Catalog Command Service

A standalone service that represents a "write" model, which is "command" part in the CQRS pattern. It uses MongoDB as an
event store. This service publishes the domain events through Kafka.
Catalog brands and catalog categories are stored in MongoDB.

The operations for inserting, updating and deleting catalog items are available only for the admin users who own a
token that contains "catalog-service" audience.

In case the price of a product is changed, the catalog command service will publish **ProductPriceChangedIntegrationEvent**.
It also handles two integration events:
1. **OrderStatusChangedToAwaitingValidationIntegrationEvent**

   To validate the quantity of the catalog items in the order that's being processed. It publishes **OrderStockConfirmedIntegrationEvent**
   in case the catalog items are available. Otherwise, it publishes **OrderStockRejectedIntegrationEvent**.

2. **OrderStatusChangedToPaidIntegrationEventHandler**

   To reduce the quantity of the catalog items from the order that has been paid successfully.


# Running the Catalog Command Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -Dspring.profiles.active=dev -jar services/catalog/catalog-command/build/libs/catalog-command.jar

Optional profiles:
1. **dev** - to insert test data.
2. **elk** - to enable ELK logging.
3. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
4. **docker** - used when the service is run with docker.
