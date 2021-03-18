# e-shop

# Integration events

## Basket
### Publishes
1. UserCheckoutAcceptedIntegrationEvent (handled by Ordering)

### Handles
1. OrderStartedIntegrationEvent (from Ordering) (triggers -nothing-)
2. ProductPriceChangedIntegrationEvent (from Catalog) (triggers -nothing-)

## Catalog
### Publishes
1. ProductPriceChangedIntegrationEvent (handled by Basket)
2. OrderStockConfirmedIntegrationEvent (handled by Ordering) (triggered by 1)
3. OrderStockRejectedIntegrationEvent (handled by Ordering) (triggered by 1)

### Handles
1. OrderStatusChangedToAwaitingValidationIntegrationEvent (from Ordering) (triggers 2 | 3)
2. OrderStatusChangedToPaidIntegrationEvent (from Ordering) (triggers -nothing-)

## Payment
### Publishes
1. OrderPaymentFailedIntegrationEvent (handled by Ordering) (triggered by 1)
2. OrderPaymentSucceededIntegrationEvent (handled by Ordering) (triggered by 1)

### Handles
1. OrderStatusChangedToStockConfirmedIntegrationEvent (from Ordering) (triggers 1 | 2)

## Ordering
### Publishes
1. OrderStartedIntegrationEvent (handled by Basket) (triggered by 6)
2. OrderStatusChangedToAwaitingValidationIntegrationEvent (handled by Catalog) (triggered by 1)
3. OrderStatusChangedToPaidIntegrationEvent (handled by Catalog) (triggered by 3)
4. OrderStatusChangedToCancelledIntegrationEvent (no handlers) (triggered by 2)
5. OrderStatusChangedToShippedIntegrationEvent (no handlers) (triggered by "/cancel" endpoint)
6. OrderStatusChangedToSubmittedIntegrationEvent (no handlers) (triggered by 1)
7. OrderStatusChangedToStockConfirmedIntegrationEvent (handled by Ordering) (triggered by 4)

### Handles
1. GracePeriodConfirmedIntegrationEvent (from Ordering - background task) (triggers 2)
2. OrderPaymentFailedIntegrationEvent (from Payment) (triggers 4)
3. OrderPaymentSucceededIntegrationEvent (from Payment) (triggers 3)
4. OrderStockConfirmedIntegrationEvent (from Catalog) (triggers 7)
5. OrderStockRejectedIntegrationEvent (from Catalog) (triggers -nothing-)
6. UserCheckoutAcceptedIntegrationEvent (from Basket) (triggers 1, 6)

# Topics and events
1. order-checkouts
    - UserCheckoutAcceptedIntegrationEvent
    
2. payment-status
    - OrderPaymentFailedIntegrationEvent
    - OrderPaymentSucceededIntegrationEvent
    
3. product-price-changes
    - ProductPriceChangedIntegrationEvent
    
4. order-stock-status
    - OrderStockRejectedIntegrationEvent
    - OrderStockConfirmedIntegrationEvent
    
5. orders
    - OrderStartedIntegrationEvent
    
6. orders-waiting-validation
    - OrderStatusChangedToAwaitingValidationIntegrationEvent
    
7. paid-orders
    - OrderStatusChangedToPaidIntegrationEvent

8. grace-period-confirmed
    - GracePeriodConfirmedIntegrationEvent

6. stock-confirmed
    - OrderStatusChangedToStockConfirmedIntegrationEvent
