package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.model.tables.Product;
import org.jooq.DSLContext;

import java.util.List;
import java.util.stream.Collectors;

import static kz.beeproduct.model.tables.Product.PRODUCT;
import static kz.beeproduct.model.tables.ProductInOrders.PRODUCT_IN_ORDERS;

public class ProductDaoImpl implements ProductDao {

    private DSLContext ctx;

    public ProductDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public List<ProductDto> findAll() {
        return ctx.selectFrom(PRODUCT).fetch().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByOrder(Long order) {
        return ctx
                .select(PRODUCT.ID_,
                        PRODUCT.TITLE_,
                        PRODUCT.PRICE_,
                        PRODUCT.AVATAR_,
                        PRODUCT.DESCRIPTION,
                        PRODUCT.PATH_,
                        PRODUCT_IN_ORDERS.AMOUNT_)
                .from(PRODUCT)
                .join(PRODUCT_IN_ORDERS)
                .on(PRODUCT.ID_.eq(PRODUCT_IN_ORDERS.PRODUCT_))
                .where(PRODUCT_IN_ORDERS.ORDER_.eq(order))
                .and(PRODUCT_IN_ORDERS.REMOVED_.eq(false))
                .fetch()
                .stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
