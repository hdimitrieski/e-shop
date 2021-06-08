package com.eshop.basket.infrastructure;

import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.CustomerBasket;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.isNull;

@Repository
public class BasketRepositoryImpl implements BasketRepository {
  private static final String BASKET_KEY = "BASKET";

  private final HashOperations<String, String, CustomerBasket> hashOperations;

  public BasketRepositoryImpl(RedisTemplate<String, CustomerBasket> redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public Optional<CustomerBasket> getBasket(String customerId) {
    return Optional.ofNullable(hashOperations.get(BASKET_KEY, customerId));
  }

  @Override
  public Set<String> getUsers() {
    return hashOperations.keys(BASKET_KEY);
  }

  @Override
  public CustomerBasket updateBasket(CustomerBasket basket) {
    basket.getItems()
        .stream().filter(basketItem -> isNull(basketItem.getId()))
        .forEach(basketItem -> basketItem.setId(UUID.randomUUID().toString()));
    hashOperations.put(BASKET_KEY, basket.getBuyerId(), basket);
    return basket;
  }

  @Override
  public void deleteBasket(String customerId) {
    hashOperations.delete(BASKET_KEY, customerId);
  }
}
