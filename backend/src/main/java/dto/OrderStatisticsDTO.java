package dto;
public class OrderStatisticsDTO {
    public Long orderId;
    public String location;
    public double totalPrice;
    public DateTime orderDate;
    public OrderStatisticsDTO(Long orderId, String location, double totalPrice) {
        this.orderId = orderId;
        this.location = location;
        this.totalPrice = totalPrice;
    }