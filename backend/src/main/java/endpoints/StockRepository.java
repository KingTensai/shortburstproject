package endpoints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Product;
import models.Stock;

import java.util.List;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {

    /**
     * Find stock entry by product entity.
     */
    public Stock findByProduct(Product product) {
        return find("product", product).firstResult();
    }

    /**
     * Find stock entry by product ID.
     */
    public Stock findByProductId(long productId) {
        return find("product.id", productId).firstResult();
    }

    /**
     * Returns all stock rows where quantity is below the given threshold.
     * Useful for admin dashboards.
     */
    public List<Stock> findLowStock(int threshold) {
        return find("quantity < ?1", threshold).list();
    }

    /**
     * Increase stock for a product.
     */
    public void increaseStock(Product product, int amount) {
        Stock stock = findByProduct(product);
        if (stock == null) {
            stock = new Stock(product, amount);
            persist(stock);
        } else {
            stock.setQuantity(stock.getQuantity() + amount);
        }
    }

    /**
     * Decrease stock for a product (does NOT check negative).
     * OrderResource still validates.
     */
    public void decreaseStock(Product product, int amount) {
        Stock stock = findByProduct(product);
        if (stock != null) {
            stock.setQuantity(stock.getQuantity() - amount);
        }
    }
}
