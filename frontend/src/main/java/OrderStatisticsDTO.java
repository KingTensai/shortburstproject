public class OrderStatisticsDTO {
    public Long orderId;
    public String location;
    public double totalPrice;

    public OrderStatisticsDTO() { } // Jackson needs a no-arg constructor
}