package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.ProductRecord;
import org.jooq.Record7;

import static kz.beeproduct.model.tables.Product.PRODUCT;
import static kz.beeproduct.model.tables.ProductInOrders.PRODUCT_IN_ORDERS;

public class ProductDto {

    public Long id;
    public String title;
    public String description;
    public String avatar;
    public String path;
    public Integer price;
    public Integer amount;
    public Boolean removed;

    public ProductDto() {
    }

    public ProductDto(ProductRecord record) {
        this.id = record.getId_();
        this.title = record.getTitle_();
        this.description = record.getDescription();
        this.avatar = record.getAvatar_();
        this.path = record.getPath_();
        this.price = record.getPrice_();
//        this.removed = record.rem
    }

    public ProductDto(Record7<Long, String, Integer, String, String, String, Integer> record) {
//                PRODUCT.ID_,
//                PRODUCT.TITLE_,
//                PRODUCT.PRICE_,
//                PRODUCT.AVATAR_,
//                PRODUCT.DESCRIPTION,
//                PRODUCT.PATH_,
//                PRODUCT_IN_ORDERS.AMOUNT_)
        this.id = record.value1();
        this.title = record.value2();
        this.price = record.value3();
        this.avatar = record.value4();
        this.description = record.value5();
        this.path = record.value6();
        this.amount = record.value7();
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                ", path='" + path + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
