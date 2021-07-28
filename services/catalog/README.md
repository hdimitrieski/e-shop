# Catalog Service

The Catalog service is a simple CRUD data-driven microservice that manages the products that are available in the shop.
The catalog items are publicly available. The operations for inserting, updating and deleting catalog items are available
only for the admin users who own a token that contains "catalog-service" audience.

In case the price of a product is changed, the catalog service will publish **ProductPriceChangedIntegrationEvent**.
It also handles two integration events:
1. **OrderStatusChangedToAwaitingValidationIntegrationEvent**

    To validate the quantity of the catalog items in the order that's being processed. It publishes **OrderStockConfirmedIntegrationEvent**
    in case the catalog items are available. Otherwise, it publishes **OrderStockRejectedIntegrationEvent**.

2. **OrderStatusChangedToPaidIntegrationEventHandler**
    
    To reduce the quantity of the catalog items from the order that has been paid successfully.

The catalog service uses [Spring JPA](https://spring.io/projects/spring-data-jpa) and Postgres. To update the database 
and publish integration event atomically, it uses the [Outbox pattern](https://microservices.io/patterns/data/transactional-outbox.html).

# Running the Catalog Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -Dspring.profiles.active=dev -jar services/catalog/target/catalog.jar

Optional profiles:
1. **dev** - to insert test data.
2. **elk** - to enable ELK logging.
3. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
4. **docker** - used when the service is run with docker.
