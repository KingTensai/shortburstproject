package endpoints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Product;
import models.Stock;

import java.util.List;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {


    public Stock findByProduct(Product product) {
        return find("product", product).firstResult();
    }

    public Stock findByProductId(long productId) {
        return find("product.id", productId).firstResult();
    }

    public List<Stock> findLowStock(int threshold) {
        return find("quantity < ?1", threshold).list();
    }

    public void increaseStock(Product product, int amount) {
        Stock stock = findByProduct(product);
        if (stock == null) {
            stock = new Stock(product, amount);
            persist(stock);
        } else {
            stock.setQuantity(stock.getQuantity() + amount);
        }
    }

    public void decreaseStock(Product product, int amount) {
        Stock stock = findByProduct(product);
        if (stock != null) {
            stock.setQuantity(stock.getQuantity() - amount);
        }
    }
}
