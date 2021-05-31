package com.eshop.analytics.services;

public interface OrderCheckoutsService {
  Long totalCheckoutsNumber();

  Long checkoutsForUser(String userId);
}
