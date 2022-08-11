# Order Grace Period Task

Currently, this microservice is implemented as a spring boot web application, but it should be re-implement as 
[Spring Cloud Task](https://spring.io/projects/spring-cloud-task) that will be run with [Spring Cloud Data Flow](https://spring.io/projects/spring-cloud-dataflow).

Order grace period task runs every 20 seconds, and it looks for confirmed orders in the orderdb. It takes every order
that has been submitted and for each one of those orders it publishes **GracePeriodConfirmedIntegrationEvent**.

# Running the Order Grace Period Task
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/order-grace-period-task/build/libs/order-grace-period-task.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
