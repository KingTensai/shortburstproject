package endpoints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
    // You can add custom queries here if needed
}