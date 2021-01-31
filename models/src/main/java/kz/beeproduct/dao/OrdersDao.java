package kz.beeproduct.dao;

import kz.beeproduct.dto.OrdersDto;

public interface OrdersDao {

    OrdersDto save(OrdersDto order);

    boolean addProduct(Long orderId, Long productId);

    boolean removeProduct(Long orderId, Long productId);

    OrdersDto findByUser(String userId);

}
