package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.OrdersDao;
import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.model.Sequences;
import kz.beeproduct.model.enums.Status;
import kz.beeproduct.model.tables.records.OrdersRecord;
import kz.beeproduct.model.tables.records.ProductInOrdersRecord;
import org.jooq.DSLContext;
import org.jooq.tools.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kz.beeproduct.model.tables.Orders.ORDERS;
import static kz.beeproduct.model.tables.ProductInOrders.PRODUCT_IN_ORDERS;

public class OrdersDaoImpl implements OrdersDao {

    private DSLContext ctx;

    public OrdersDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public OrdersDto create(OrdersDto order) {
        OrdersRecord record = ctx.newRecord(ORDERS);
        record.setId_(ctx.nextval(Sequences.HONEYBEE_SEQ));
        record.setOrderTime_(LocalDateTime.now());
        record.setUser_(order.user);
        if(!StringUtils.isEmpty(order.status)) {
            record.setStatus_(Status.valueOf(order.status));
        }
        record.insert();
        return new OrdersDto(record);
    }

    @Override
    public OrdersDto update(OrdersDto order) {
        OrdersRecord record = _findById(order.id);
        record.setId_(order.id);
        record.setOrderTime_(LocalDateTime.now());
        record.setUser_(order.user);
        if(!StringUtils.isEmpty(order.status)) {
            record.setStatus_(Status.valueOf(order.status));
        }
        record.update();
        return new OrdersDto(record);
    }

    @Override
    public boolean addProduct(Long orderId, Long productId) {
        OrdersRecord record = _findById(orderId);
        if (record != null) {
            ProductInOrdersRecord productInOrdersRecord = ctx.newRecord(PRODUCT_IN_ORDERS);
            productInOrdersRecord.setOrder_(orderId);
            productInOrdersRecord.setProduct_(productId);
            productInOrdersRecord.setAmount_(1);
            productInOrdersRecord.insert();
            return true;
        }
        return false;
    }

    @Override
    public Integer updateProduct(Long orderId, Long productId, Integer amount) {

        return ctx.update(PRODUCT_IN_ORDERS)
                .set(PRODUCT_IN_ORDERS.AMOUNT_, amount)
                .where(PRODUCT_IN_ORDERS.ORDER_.eq(orderId)
                        .and(PRODUCT_IN_ORDERS.PRODUCT_.eq(productId)))
                .execute();

    }

    @Override
    public Integer removeProduct(Long orderId, Long productId) {
        return ctx.update(PRODUCT_IN_ORDERS)
                .set(PRODUCT_IN_ORDERS.REMOVED_, true)
                .where(PRODUCT_IN_ORDERS.ORDER_.eq(orderId)
                        .and(PRODUCT_IN_ORDERS.PRODUCT_.eq(productId)))
                .execute();
    }

    @Override
    public OrdersDto findByUser(String userId) {
        OrdersRecord record = ctx.selectFrom(ORDERS)
                .where(ORDERS.USER_.eq(userId))
                .and(ORDERS.STATUS_.isNull())
                .fetchOne();
        return record == null ? null : new OrdersDto(record);
    }

    @Override
    public OrdersDto findByUserAndStatus(String userId, Status status) {
        OrdersRecord record = ctx.selectFrom(ORDERS)
                .where(ORDERS.USER_.eq(userId))
                .and(ORDERS.STATUS_.eq(status))
                .fetchOne();
        return record == null ? null : new OrdersDto(record);
    }

    @Override
    public List<OrdersDto> getAll(Integer page) {
        int amount = 25;
        int offset = amount * (page-1);
        return ctx.selectFrom(ORDERS).orderBy(ORDERS.ORDER_TIME_.desc()).limit(offset, amount).fetch().stream().map(OrdersDto::new).collect(Collectors.toList());
    }

    private OrdersRecord _findById(Long id) {
        return ctx.selectFrom(ORDERS).where(ORDERS.ID_.eq(id)).fetchOne();
    }

    @Override
    public int getCount() {
        return ctx.selectFrom(ORDERS).fetch().size();
    }
}
