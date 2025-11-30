package src.main.java.dto;

import java.util.List;

public class OrderRequestDTO {

    public String orderLocation;

    public List<OrderProductDTO> products;

    public static class OrderProductDTO {
        public Long productId;
        public String productName;
        public String productDescription;
        public double productPrice;
        public int quantity;
    }
}
