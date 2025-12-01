package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private int quantity;

    public Stock() { }

    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        if (product != null && product.getStock() != this) {
            product.setStock(this);
        }
    }

    /* Getters / setters */

    public Long getId() { return id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        this.product = product;
        if (product != null && product.getStock() != this) {
            product.setStock(this);
        }
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
