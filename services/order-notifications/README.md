# Order Notifications Service

This service is used to notify the user in real-time about the order process. It implements messaging using
the [Spring Websocket](https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html)
It basically listens to Kafka topics related to the order status and pushes messages to the appropriate queue.

# Running the Order Notifications Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/order-notifications/build/libs/order-notifications.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
