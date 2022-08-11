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

## Prepare certs

### Export certificate from keycloak server
Exports certificate from keycloak server, use `authorization-service`:
```
echo | openssl s_client -servername authorization-service -connect authorization-service:8443 |\
  sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > certificate.crt
```

### Import certificate
See: https://github.com/Odyquest/Odyquest/issues/26
https://www.baeldung.com/java-ssl-handshake-failures

```
keytool -importcert -cacerts -storepass changeit \
-ext SAN=DNS:"localhost",DNS:"authorization-service",IP:127.0.0.1 \
-ext CN="authorization-service" \
-file certificate.crt -alias "eshop"
```

Here use `-cacerts` instead of `-keystore $JAVA_HOME/lib/security/cacerts` to get rid of:
```
Warning: use -cacerts option to access cacerts keystore
```

### Some useful command for list cert
list all cert:
```
keytool -list -cacerts -v
```
review `eshop` cert:
```
keytool -list -cacerts -v -alias eshop
```
review `jks`:
```
keytool -v -list -keystore auth-server.keystore
```

### Delete cert
```
keytool -delete -cacerts -alias eshop
```

# Running the API Gateway Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar infrastructure/gateway/build/libs/gateway.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
