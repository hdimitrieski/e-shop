package com.eshop.analytics.controllers;

import com.eshop.analytics.model.Order;
import com.eshop.analytics.model.Product;
import com.eshop.analytics.services.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
  private static final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);

  private final OrderCheckoutsService orderCheckoutsService;
  private final BestSellingProductsService bestSellingProductsService;
  private final OrdersService ordersService;
  private final IncomeService incomeService;
  private final PaymentsService paymentsService;

  @RequestMapping("/order-checkouts")
  public Long totalNumberOfOrderCheckouts() {
    logger.info("Retrieving total number of checkouts");
    return orderCheckoutsService.totalCheckoutsNumber();
  }

  @RequestMapping("/order-checkouts/{userId}")
  public Long getOrderCheckouts(@PathVariable String userId) {
    logger.info("Retrieving number of order checkouts for user: {}", userId);
    return orderCheckoutsService.checkoutsForUser(userId);
  }

  @RequestMapping("/products/top-five")
  public List<Product> getTopFiveProducts(@RequestParam(value = "category", required = false) String category) {
    logger.info("Retrieving top five products");
    return bestSellingProductsService.topFiveProducts();
  }

  @RequestMapping("/orders/{orderId}")
  public Order getOrders(@PathVariable String orderId, @RequestParam(value = "status") String status) {
    logger.info("Retrieving all submitted orders");
    return "submitted".equals(status)
        ? ordersService.getSubmittedOrder(orderId)
        : ordersService.getPaidOrder(orderId);
  }

  @RequestMapping("/total-income")
  public Double getTotalIncome() {
    logger.info("Get total income");
    return incomeService.totalIncome();
  }

  @RequestMapping("/payments/count")
  public Long getPaymentsCount() {
    logger.info("Get payments count");
    return paymentsService.count();
  }
}
