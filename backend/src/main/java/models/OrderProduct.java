package src.main.java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    /**
     * Cached line total (product.price * quantity).
     * Kept for convenience but recalculated when product/quantity changes.
     */
    private double price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore // avoid serializing back to order (prevents cycles)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderProduct() { }

    public OrderProduct(int quantity, Order order, Product product) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
        recalculatePrice();
    }

    /* Getters / setters */

    public Long getId() { return id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalculatePrice();
    }

    public double getPrice() { return price; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        this.product = product;
        recalculatePrice();
    }

    private void recalculatePrice() {
        if (product != null) {
            this.price = product.getPrice() * this.quantity;
        } else {
            this.price = 0.0;
        }
    }
}
