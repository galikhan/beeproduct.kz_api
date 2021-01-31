package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.OrdersRecord;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDto {

    public Long id;
    public String user;
    public LocalDateTime orderTime;
    public Integer amount;
    public List<ProductDto> products;

    public OrdersDto() {
    }

    public OrdersDto(OrdersRecord record) {
        this.id = record.getId_();
        this.user =record.getUser_();
        this.orderTime = record.getOrderTime_();
        this.amount = record.getAmount_();
    }

    @Override
    public String toString() {
        return "OrdersDto{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", orderTime=" + orderTime +
                ", amount=" + amount +
                '}';
    }
}
