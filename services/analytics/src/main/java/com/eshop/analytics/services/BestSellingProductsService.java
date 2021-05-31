package com.eshop.analytics.services;

import com.eshop.analytics.model.Product;

import java.util.List;

public interface BestSellingProductsService {
  List<Product> topFiveProducts();
}
