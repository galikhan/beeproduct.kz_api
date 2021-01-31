package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.OrdersDao;
import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.model.Sequences;
import kz.beeproduct.model.tables.records.OrdersRecord;
import kz.beeproduct.model.tables.records.ProductInOrdersRecord;
import org.jooq.DSLContext;

import java.time.LocalDateTime;

import static kz.beeproduct.model.tables.Orders.ORDERS;
import static kz.beeproduct.model.tables.ProductInOrders.PRODUCT_IN_ORDERS;

public class OrdersDaoImpl implements OrdersDao {

    private DSLContext ctx;

    public OrdersDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public OrdersDto save(OrdersDto order) {
        OrdersRecord record = ctx.newRecord(ORDERS);
        record.setAmount_(order.amount);
        record.setId_(ctx.nextval(Sequences.HONEYBEE_SEQ));
        record.setOrderTime_(LocalDateTime.now());
        record.setUser_(order.user);
        record.insert();
        return new OrdersDto(record);
    }

    @Override
    public boolean addProduct(Long orderId, Long productId) {
        OrdersRecord record = _findById(orderId);
        if(record != null) {
            ProductInOrdersRecord productInOrdersRecord = ctx.newRecord(PRODUCT_IN_ORDERS);
            productInOrdersRecord.setOrder_(orderId);
            productInOrdersRecord.setProduct_(productId);
            productInOrdersRecord.insert();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProduct(Long orderId, Long productId) {
        int result = ctx.deleteFrom(PRODUCT_IN_ORDERS)
                .where(PRODUCT_IN_ORDERS.ORDER_.eq(orderId).and(PRODUCT_IN_ORDERS.PRODUCT_.eq(productId)))
                .execute();
        return true;
    }

    @Override
    public OrdersDto findByUser(String userId) {
        OrdersRecord record = ctx.selectFrom(ORDERS).where(ORDERS.USER_.eq(userId)).fetchOne();
        return record == null ? null: new OrdersDto(record);
    }

    private OrdersRecord _findById(Long id) {
        return ctx.selectFrom(ORDERS).where(ORDERS.ID_.eq(id)).fetchOne();
    }
}
