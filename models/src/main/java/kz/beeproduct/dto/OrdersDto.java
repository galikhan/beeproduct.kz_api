package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.OrdersRecord;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDto {

    public Long id;
    public String user;
    public LocalDateTime orderTime;
    public List<ProductDto> products;
    public String status;

    public OrdersDto() {
    }

    public OrdersDto(OrdersRecord record) {
        this.id = record.getId_();
        this.user = record.getUser_();
        this.orderTime = record.getOrderTime_();
        if (record.getStatus_() != null) {
            this.status = record.getStatus_().getLiteral();
        }
    }

    @Override
    public String toString() {
        return "OrdersDto{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", orderTime=" + orderTime +
                '}';
    }
}
