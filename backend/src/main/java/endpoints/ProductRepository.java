package src.main.java.endpoints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Product;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    /**
     * Find a product by SKU (unique field).
     */
    public Product findBySku(String sku) {
        return find("sku", sku).firstResult();
    }

    /**
     * Search by product name (case-insensitive).
     */
    public List<Product> searchByName(String name) {
        return find("LOWER(name) LIKE ?1", "%" + name.toLowerCase() + "%").list();
    }

    /**
     * Returns all products in a given category.
     */
    public List<Product> findByCategory(String category) {
        return find("category", category).list();
    }

    /**
     * Returns products stored at a specific warehouse/location.
     */
    public List<Product> findByLocation(String location) {
        return find("location", location).list();
    }
}
