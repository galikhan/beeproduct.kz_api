package kz.beeproduct.dao;

import kz.beeproduct.dto.ProductDto;

import java.util.List;

public interface ProductDao {

    List<ProductDto> findAll();

    List<ProductDto> findByOrder(Long order);
}
