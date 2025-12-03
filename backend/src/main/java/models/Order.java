package models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // "order" is a reserved SQL word
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;

    @Column(nullable = false)
    private String orderLocation;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> products = new ArrayList<>();

    public Order() { }

    public Order(String orderLocation, LocalDateTime orderDate) {
        this.orderLocation = orderLocation;
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return products.stream()
                .mapToDouble(OrderProduct::getPrice)
                .sum();
    }

    public Long getId() { return id; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getOrderLocation() { return orderLocation; }
    public void setOrderLocation(String orderLocation) { this.orderLocation = orderLocation; }

    public List<OrderProduct> getProducts() { return products; }

    public void setProducts(List<OrderProduct> products) {
        this.products.clear();
        if (products != null) {
            for (OrderProduct op : products) {
                addProduct(op);
            }
        }
    }

    public void addProduct(OrderProduct op) {
        if (op == null) return;
        op.setOrder(this);
        products.add(op);
    }

    public void removeProduct(OrderProduct op) {
        if (op == null) return;
        products.remove(op);
        op.setOrder(null);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
