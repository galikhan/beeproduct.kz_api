package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.ProductRecord;

public class ProductDto {

    public Long id;
    public String title;
    public String description;
    public String avatar;
    public String path;
    public Integer price;

    public ProductDto(ProductRecord record) {
        this.id = record.getId_();
        this.title = record.getTitle_();
        this.description = record.getDescription();
        this.avatar = record.getAvatar_();
        this.path = record.getPath_();
        this.price = record.getPrice_();
    }

    public ProductDto() {
    }
}
