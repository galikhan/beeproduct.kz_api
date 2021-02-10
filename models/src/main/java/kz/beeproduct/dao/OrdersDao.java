package kz.beeproduct.dao;

import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.model.enums.Status;

public interface OrdersDao {

    OrdersDto create(OrdersDto order);

    OrdersDto update(OrdersDto order);

    boolean addProduct(Long orderId, Long productId);

    Integer updateProduct(Long orderId, Long productId, Integer amount);

    Integer removeProduct(Long orderId, Long productId);

    OrdersDto findByUser(String userId);

    OrdersDto findByUserAndStatus(String userId, Status status);
}
