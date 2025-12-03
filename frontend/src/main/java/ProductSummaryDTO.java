import java.io.Serializable;
import java.time.LocalDate;

public class ProductSummaryDTO implements Serializable {
    private Long productId;
    private String name;
    private int stock;
    private String location;

    public ProductSummaryDTO() { }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public String getLocation() { return location; }
}
