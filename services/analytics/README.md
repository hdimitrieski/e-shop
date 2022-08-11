Analytics Service
===

This service serves as an example for real-time stream processing using [Kafka Streams API](https://docs.confluent.io/platform/current/streams/index.html).

The stream processors are used to transform data. The processors usually use operations such as map, filter, join, 
aggregate to process a stream. A stream processor receives one event at a time, applies its operation to it, and may
produce one or more output events.

Stream processors, store processed data in an in-memory state store.

The data stored in the state store is exposed via JSON API. The admin users have access to all endpoints, and the users
who own a JWT token with "analytics-products" scope have access to the products' analytics.

Read more about Kafka Streams:
- https://www.confluent.io/blog/introducing-kafka-streams-stream-processing-made-simple/
- https://docs.confluent.io/platform/current/streams/index.html

### Running the Analytics Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/analytics/build/libs/analytics.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
