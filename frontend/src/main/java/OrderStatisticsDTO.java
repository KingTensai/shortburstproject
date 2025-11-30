import java.time.LocalDateTime;

public class OrderStatisticsDTO {

    private Long orderId;
    private String location;
    private double totalPrice;
    private LocalDateTime orderDate;

    public OrderStatisticsDTO() {}

    public OrderStatisticsDTO(Long orderId, String location, double totalPrice, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.location = location;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
