package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    private String category;

    private String location; // e.g., warehouse location

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Stock stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore // avoid serializing orderProducts by default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Product() { }

    public Product(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /* Getters / setters */

    public Long getId() { return id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Stock getStock() { return stock; }
    public void setStock(Stock stock) {
        this.stock = stock;
        if (stock != null && stock.getProduct() != this) {
            stock.setProduct(this);
        }
    }

    public List<OrderProduct> getOrderProducts() { return orderProducts; }

    public void addOrderProduct(OrderProduct op) {
        if (op == null) return;
        op.setProduct(this);
        orderProducts.add(op);
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() { this.updatedAt = LocalDateTime.now(); }
}
