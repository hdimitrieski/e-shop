package com.eshop.analytics.model;

import java.util.*;

/**
 * Used in aggregations to keep track of the top five products.
 */
public class TopFiveProducts implements Iterable<ProductSellCount> {
  private final Map<UUID, ProductSellCount> currentProducts = new HashMap<>();
  private final TreeSet<ProductSellCount> topFive = new TreeSet<>((o1, o2) -> {
    final int result = o2.getSells().compareTo(o1.getSells());
    if (result != 0) {
      return result;
    }
    return o1.getProductId().compareTo(o2.getProductId());
  });

  public void add(final ProductSellCount productSellCount) {
    if (currentProducts.containsKey(productSellCount.getProductId())) {
      topFive.remove(currentProducts.remove(productSellCount.getProductId()));
    }
    topFive.add(productSellCount);
    currentProducts.put(productSellCount.getProductId(), productSellCount);
    if (topFive.size() > 5) {
      final ProductSellCount last = topFive.last();
      currentProducts.remove(last.getProductId());
      topFive.remove(last);
    }
  }

  public void remove(final ProductSellCount value) {
    topFive.remove(value);
    currentProducts.remove(value.getProductId());
  }

  @Override
  public Iterator<ProductSellCount> iterator() {
    return topFive.iterator();
  }
}
