/*
 * This file is generated by jOOQ.
 */
package kz.beeproduct.model.tables.records;


import java.time.LocalDateTime;

import kz.beeproduct.model.enums.Status;
import kz.beeproduct.model.tables.Orders;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrdersRecord extends UpdatableRecordImpl<OrdersRecord> implements Record4<Long, String, LocalDateTime, Status> {

    private static final long serialVersionUID = 812031221;

    /**
     * Setter for <code>public.orders.id_</code>.
     */
    public OrdersRecord setId_(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.orders.id_</code>.
     */
    public Long getId_() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.orders.user_</code>.
     */
    public OrdersRecord setUser_(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.orders.user_</code>.
     */
    public String getUser_() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.orders.order_time_</code>.
     */
    public OrdersRecord setOrderTime_(LocalDateTime value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.orders.order_time_</code>.
     */
    public LocalDateTime getOrderTime_() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.orders.status_</code>.
     */
    public OrdersRecord setStatus_(Status value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.orders.status_</code>.
     */
    public Status getStatus_() {
        return (Status) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, LocalDateTime, Status> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, LocalDateTime, Status> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Orders.ORDERS.ID_;
    }

    @Override
    public Field<String> field2() {
        return Orders.ORDERS.USER_;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Orders.ORDERS.ORDER_TIME_;
    }

    @Override
    public Field<Status> field4() {
        return Orders.ORDERS.STATUS_;
    }

    @Override
    public Long component1() {
        return getId_();
    }

    @Override
    public String component2() {
        return getUser_();
    }

    @Override
    public LocalDateTime component3() {
        return getOrderTime_();
    }

    @Override
    public Status component4() {
        return getStatus_();
    }

    @Override
    public Long value1() {
        return getId_();
    }

    @Override
    public String value2() {
        return getUser_();
    }

    @Override
    public LocalDateTime value3() {
        return getOrderTime_();
    }

    @Override
    public Status value4() {
        return getStatus_();
    }

    @Override
    public OrdersRecord value1(Long value) {
        setId_(value);
        return this;
    }

    @Override
    public OrdersRecord value2(String value) {
        setUser_(value);
        return this;
    }

    @Override
    public OrdersRecord value3(LocalDateTime value) {
        setOrderTime_(value);
        return this;
    }

    @Override
    public OrdersRecord value4(Status value) {
        setStatus_(value);
        return this;
    }

    @Override
    public OrdersRecord values(Long value1, String value2, LocalDateTime value3, Status value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrdersRecord
     */
    public OrdersRecord() {
        super(Orders.ORDERS);
    }

    /**
     * Create a detached, initialised OrdersRecord
     */
    public OrdersRecord(Long id_, String user_, LocalDateTime orderTime_, Status status_) {
        super(Orders.ORDERS);

        set(0, id_);
        set(1, user_);
        set(2, orderTime_);
        set(3, status_);
    }
}
