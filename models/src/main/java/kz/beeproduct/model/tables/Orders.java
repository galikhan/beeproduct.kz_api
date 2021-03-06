/*
 * This file is generated by jOOQ.
 */
package kz.beeproduct.model.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import kz.beeproduct.model.Keys;
import kz.beeproduct.model.Public;
import kz.beeproduct.model.enums.Status;
import kz.beeproduct.model.tables.records.OrdersRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Orders extends TableImpl<OrdersRecord> {

    private static final long serialVersionUID = 1762589119;

    /**
     * The reference instance of <code>public.orders</code>
     */
    public static final Orders ORDERS = new Orders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrdersRecord> getRecordType() {
        return OrdersRecord.class;
    }

    /**
     * The column <code>public.orders.id_</code>.
     */
    public final TableField<OrdersRecord, Long> ID_ = createField(DSL.name("id_"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.orders.user_</code>.
     */
    public final TableField<OrdersRecord, String> USER_ = createField(DSL.name("user_"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.orders.order_time_</code>.
     */
    public final TableField<OrdersRecord, LocalDateTime> ORDER_TIME_ = createField(DSL.name("order_time_"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>public.orders.status_</code>.
     */
    public final TableField<OrdersRecord, Status> STATUS_ = createField(DSL.name("status_"), org.jooq.impl.SQLDataType.VARCHAR.asEnumDataType(kz.beeproduct.model.enums.Status.class), this, "");

    /**
     * Create a <code>public.orders</code> table reference
     */
    public Orders() {
        this(DSL.name("orders"), null);
    }

    /**
     * Create an aliased <code>public.orders</code> table reference
     */
    public Orders(String alias) {
        this(DSL.name(alias), ORDERS);
    }

    /**
     * Create an aliased <code>public.orders</code> table reference
     */
    public Orders(Name alias) {
        this(alias, ORDERS);
    }

    private Orders(Name alias, Table<OrdersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Orders(Name alias, Table<OrdersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Orders(Table<O> child, ForeignKey<O, OrdersRecord> key) {
        super(child, key, ORDERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<OrdersRecord> getPrimaryKey() {
        return Keys.ORDERS_PKEY;
    }

    @Override
    public List<UniqueKey<OrdersRecord>> getKeys() {
        return Arrays.<UniqueKey<OrdersRecord>>asList(Keys.ORDERS_PKEY);
    }

    @Override
    public List<ForeignKey<OrdersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<OrdersRecord, ?>>asList(Keys.ORDERS__ORDERS_USER__FKEY);
    }

    public Users users() {
        return new Users(this, Keys.ORDERS__ORDERS_USER__FKEY);
    }

    @Override
    public Orders as(String alias) {
        return new Orders(DSL.name(alias), this);
    }

    @Override
    public Orders as(Name alias) {
        return new Orders(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Orders rename(String name) {
        return new Orders(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Orders rename(Name name) {
        return new Orders(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, LocalDateTime, Status> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
