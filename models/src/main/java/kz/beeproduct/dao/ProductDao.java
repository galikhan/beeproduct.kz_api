package kz.beeproduct.dao;

import kz.beeproduct.dto.ProductDto;

import java.util.List;

public interface ProductDao {

    List<ProductDto> findAll();

    List<ProductDto> findByOrder(Long order);

    ProductDto create(ProductDto product);

    ProductDto update(ProductDto product);

    int delete(ProductDto product);

    ProductDto findById(Long productId);

    boolean alreadyAdded(Long productId, Long order);
}
