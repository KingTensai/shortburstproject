import java.time.LocalDateTime;

public class OrderStatisticsDTO {
    public Long orderId;
    public String location;
    public double totalPrice;
    public LocalDateTime orderDate;
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }



    public OrderStatisticsDTO(Long orderId, String location, double totalPrice) {
        this.orderId = orderId;
        this.location = location;
        this.totalPrice = totalPrice;
    }
    public OrderStatisticsDTO() {}
}