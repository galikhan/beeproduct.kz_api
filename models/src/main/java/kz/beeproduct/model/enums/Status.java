/*
 * This file is generated by jOOQ.
 */
package kz.beeproduct.model.enums;


import kz.beeproduct.model.Public;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum Status implements EnumType {

    started("started"),

    confirmed("confirmed"),

    finished("finished");

    private final String literal;

    private Status(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}