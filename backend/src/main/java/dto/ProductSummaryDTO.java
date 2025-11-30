package src.main.java.dto;

public class ProductSummaryDTO {
    public Long productId;
    public String name;
    public int stock;

    public ProductSummaryDTO(Long productId, String name, int stock) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
    }
    public ProductSummaryDTO() {}
}
