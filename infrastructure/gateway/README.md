# API Gateway Service

The API Gateway service is implemented using [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/single/spring-cloud-gateway.html).
It provides non-blocking asynchronous request processing, and it has much better performance compared to Netflix Zuul.
It also implements the [Backend for Frontend pattern (BFF)](https://samnewman.io/patterns/architectural/bff/) and it 
exposes REST API for the SPA. If we implement a mobile application, we would implement a dedicated API gateway for it.

When the client sends a requests to the gateway, it either forwards the call directly to an internal microservice, or
it calls one or more internal microservices, aggregates the results and returns data to the client.

The API gateway uses the [Circuit breaker pattern](https://microservices.io/patterns/reliability/circuit-breaker.html) 
to invoke the services. The circuit breaker pattern is implemented using [Spring Cloud Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)
and [Resilience4j](https://github.com/resilience4j/resilience4j).

Read more about BFF and API Gateway patterns:
- https://samnewman.io/patterns/architectural/bff/
- https://microservices.io/patterns/apigateway.html
- https://docs.microsoft.com/en-us/dotnet/architecture/microservices/architect-microservice-container-applications/direct-client-to-microservice-communication-versus-the-api-gateway-pattern

# Running the API Gateway Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar infrastructure/gateway/target/gateway.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
