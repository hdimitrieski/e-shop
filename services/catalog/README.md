# Catalog Service

This module contains two services: [Catalog Command Service](catalog-command) and [Catalog Query Service](catalog-query).
The services are implemented following the Domain Driven Design approach, [CQRS](https://martinfowler.com/bliki/CQRS.html)
and [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) implementation using [Axon](https://axoniq.io/).
We use MongoDB as an event store, and Kafka to distribute the events between the command and the query side.

The services are used to manage the products that are available in the shop. The catalog items are publicly available.
The operations for inserting, updating and deleting catalog items are available only for the admin users who own a 
token that contains "catalog-service" audience.
