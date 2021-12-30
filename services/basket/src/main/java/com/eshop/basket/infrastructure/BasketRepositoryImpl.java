package com.eshop.basket.infrastructure;

import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.BasketStatus;
import com.eshop.basket.model.CustomerBasket;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Repository
public class BasketRepositoryImpl implements BasketRepository {
  private static final String BASKET_KEY = "BASKET";

  private final HashOperations<String, UUID, CustomerBasket> hashOperations;

  public BasketRepositoryImpl(RedisTemplate<String, CustomerBasket> redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public Optional<CustomerBasket> findByCustomerId(String customerId) {
    var basketIds = hashOperations.keys(BASKET_KEY);
    return hashOperations.multiGet(BASKET_KEY, basketIds).stream()
      .filter(basket -> basket.getBuyerId().equals(customerId))
      .filter(customerBasket -> BasketStatus.New.equals(customerBasket.getStatus()))
      .findFirst();
  }

  @Override
  public Optional<CustomerBasket> findById(UUID basketId) {
    return Optional.ofNullable(hashOperations.get(BASKET_KEY, basketId));
  }

  @Override
  public Set<String> getUsers() {
    var basketIds = hashOperations.keys(BASKET_KEY);
    return hashOperations.multiGet(BASKET_KEY, basketIds).stream()
      .filter(customerBasket -> BasketStatus.New.equals(customerBasket.getStatus()))
      .map(CustomerBasket::getBuyerId)
      .collect(Collectors.toSet());
  }

  @Override
  public CustomerBasket updateBasket(CustomerBasket basket) {
    basket.getItems().stream()
      .filter(basketItem -> isNull(basketItem.getId()))
      .forEach(basketItem -> basketItem.setId(UUID.randomUUID().toString()));
    hashOperations.put(BASKET_KEY, basket.getId(), basket);
    return basket;
  }

  @Override
  public void delete(UUID basketId) {
    hashOperations.delete(BASKET_KEY, basketId);
  }
}
