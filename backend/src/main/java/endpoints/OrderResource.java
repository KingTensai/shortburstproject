package endpoints;

import dto.OrderRequestDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Order;
import models.OrderProduct;
import models.Product;
import models.Stock;

import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    ProductRepository productRepo;

    @Inject
    StockRepository stockRepo;

    @Inject
    OrderRepository orderRepo;

    /** Process an incoming order */
    @POST
    @Path("orders")
    @Transactional
    public Response createOrder(OrderRequestDTO request) {
        Order order = new Order(request.orderLocation);

        for (OrderRequestDTO.OrderProductDTO dto : request.products) {
            Product product = productRepo.findById(dto.productId);
            if (product == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Product ID " + dto.productId + " not found")
                        .build();
            }

            Stock stock = stockRepo.findByProduct(product);
            if (stock == null || stock.getQuantity() < dto.quantity) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Not enough stock for product: " + product.getName())
                        .build();
            }

            // Deduct stock
            stock.setQuantity(stock.getQuantity() - dto.quantity);

            // Create order line item
            OrderProduct op = new OrderProduct(dto.quantity, order, product);
            order.addProduct(op);
        }

        orderRepo.persist(order);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    /** Get summary of all orders */
    @GET
    @Path("orders/statistics")
    public List<OrderStatisticsDTO> getOrderStatistics() {
        return orderRepo.listAll().stream()
                .map(o -> new OrderStatisticsDTO(o.getId(), o.getOrderLocation(), o.getTotalPrice()))
                .collect(Collectors.toList());
    }

    /** Get summary of all products with stock */
    @GET
    @Path("products/statistics")
    public List<ProductSummaryDTO> getProductStatistics() {
        return productRepo.listAll().stream()
                .map(p -> new ProductSummaryDTO(
                        p.getId(),
                        p.getName(),
                        p.getStock() != null ? p.getStock().getQuantity() : 0
                ))
                .collect(Collectors.toList());
    }


}
