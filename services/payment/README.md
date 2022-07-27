# Payment Service

Simulates a simple payment gateway. It listens to **OrderStatusChangedToStockConfirmedIntegrationEvent** and produces 
either **OrderPaymentSucceededIntegrationEvent** (70% chance) or **OrderPaymentFailedIntegrationEvent** (30% chance).

# Running the Payment Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/payment/build/libs/payment.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
