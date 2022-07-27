# Catalog Query Service

A standalone service that represents a read model, which is "query" part in the CQRS pattern. To update the read model, 
it handles domain events published by catalog command service.

The catalog query service uses [Spring JPA](https://spring.io/projects/spring-data-jpa) and Postgres to store catalog 
items.

The catalog items are publicly available.

# Running the Catalog Query Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/catalog/catalog-query/build/libs/catalog-query.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
