package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.model.Sequences;
import kz.beeproduct.model.tables.records.ProductRecord;
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

    @Override
    public ProductDto create(ProductDto product) {
        ProductRecord record = ctx.newRecord(PRODUCT);
        record.setId_(ctx.nextval(Sequences.HONEYBEE_SEQ));
        record.setTitle_(product.title);
        record.setDescription(product.description);
        record.setPrice_(product.price);
        record.insert();
        return new ProductDto(record);
    }

    @Override
    public ProductDto update(ProductDto product) {
        ProductRecord record = _findById(product.id);
        record.setTitle_(product.title);
        record.setDescription(product.description);
        record.setPrice_(product.price);
        record.setAvatar_(product.avatar);
        record.update();
        return new ProductDto(record);
    }

    private ProductRecord _findById(Long id) {
        return ctx.selectFrom(PRODUCT).where(PRODUCT.ID_.eq(id)).fetchOne();
    }

    @Override
    public int delete(ProductDto product) {
        return 1;
    }

    @Override
    public ProductDto findById(Long productId) {
        ProductRecord productRecord = _findById(productId);
        return productRecord != null ? new ProductDto(productRecord) : null;
    }
}
