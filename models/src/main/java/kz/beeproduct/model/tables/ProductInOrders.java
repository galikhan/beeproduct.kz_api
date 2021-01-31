/*
 * This file is generated by jOOQ.
 */
package kz.beeproduct.model.tables;


import java.util.Arrays;
import java.util.List;

import kz.beeproduct.model.Keys;
import kz.beeproduct.model.Public;
import kz.beeproduct.model.tables.records.ProductInOrdersRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductInOrders extends TableImpl<ProductInOrdersRecord> {

    private static final long serialVersionUID = -1288808884;

    /**
     * The reference instance of <code>public.product_in_orders</code>
     */
    public static final ProductInOrders PRODUCT_IN_ORDERS = new ProductInOrders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductInOrdersRecord> getRecordType() {
        return ProductInOrdersRecord.class;
    }

    /**
     * The column <code>public.product_in_orders.product_</code>.
     */
    public final TableField<ProductInOrdersRecord, Long> PRODUCT_ = createField(DSL.name("product_"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.product_in_orders.order_</code>.
     */
    public final TableField<ProductInOrdersRecord, Long> ORDER_ = createField(DSL.name("order_"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.product_in_orders.amount_</code>.
     */
    public final TableField<ProductInOrdersRecord, Integer> AMOUNT_ = createField(DSL.name("amount_"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.product_in_orders</code> table reference
     */
    public ProductInOrders() {
        this(DSL.name("product_in_orders"), null);
    }

    /**
     * Create an aliased <code>public.product_in_orders</code> table reference
     */
    public ProductInOrders(String alias) {
        this(DSL.name(alias), PRODUCT_IN_ORDERS);
    }

    /**
     * Create an aliased <code>public.product_in_orders</code> table reference
     */
    public ProductInOrders(Name alias) {
        this(alias, PRODUCT_IN_ORDERS);
    }

    private ProductInOrders(Name alias, Table<ProductInOrdersRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProductInOrders(Name alias, Table<ProductInOrdersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> ProductInOrders(Table<O> child, ForeignKey<O, ProductInOrdersRecord> key) {
        super(child, key, PRODUCT_IN_ORDERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<ForeignKey<ProductInOrdersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProductInOrdersRecord, ?>>asList(Keys.PRODUCT_IN_ORDERS__PRODUCT_IN_ORDERS_PRODUCT__FKEY, Keys.PRODUCT_IN_ORDERS__PRODUCT_IN_ORDERS_ORDER__FKEY);
    }

    public Product product() {
        return new Product(this, Keys.PRODUCT_IN_ORDERS__PRODUCT_IN_ORDERS_PRODUCT__FKEY);
    }

    public Orders orders() {
        return new Orders(this, Keys.PRODUCT_IN_ORDERS__PRODUCT_IN_ORDERS_ORDER__FKEY);
    }

    @Override
    public ProductInOrders as(String alias) {
        return new ProductInOrders(DSL.name(alias), this);
    }

    @Override
    public ProductInOrders as(Name alias) {
        return new ProductInOrders(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductInOrders rename(String name) {
        return new ProductInOrders(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductInOrders rename(Name name) {
        return new ProductInOrders(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}