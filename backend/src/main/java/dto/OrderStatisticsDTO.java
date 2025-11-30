package src.main.java.dto;

import java.time.LocalDateTime;

public class OrderStatisticsDTO {
    public Long orderId;
    public String location;
    public double totalPrice;

    public LocalDateTime orderDate;

    public OrderStatisticsDTO(Long orderId, String location, double totalPrice, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.location = location;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }
}