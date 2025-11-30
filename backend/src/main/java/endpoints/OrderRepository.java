package src.main.java.endpoints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Order;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    /**
     * Find orders by location.
     */
    public List<Order> findByLocation(String location) {
        return find("orderLocation", location).list();
    }

    /**
     * Find all orders above a certain total price.
     */
    public List<Order> findByMinTotalPrice(double minPrice) {
        return listAll().stream()
                .filter(o -> o.getTotalPrice() >= minPrice)
                .toList();
    }

    /**
     * Get order statistics: total number of orders and sum of all order totals.
     */
    public OrderStatistics getOrderStatistics() {
        double totalRevenue = listAll().stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
        long totalOrders = count();
        return new OrderStatistics(totalOrders, totalRevenue);
    }

    public static class OrderStatistics {
        public long totalOrders;
        public double totalRevenue;

        public OrderStatistics(long totalOrders, double totalRevenue) {
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
        }
    }
}