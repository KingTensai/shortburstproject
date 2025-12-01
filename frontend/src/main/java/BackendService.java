import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class BackendService {

    private static final String BASE_URL = "http://localhost:9090";
    private final ObjectMapper mapper;

    public BackendService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<ProductSummaryDTO> getProductStatistics() throws IOException {
        URL url = new URL(BASE_URL + "/products/statistics");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        ProductSummaryDTO[] arr =
                mapper.readValue(conn.getInputStream(), ProductSummaryDTO[].class);

        return Arrays.asList(arr);
    }

    public List<OrderStatisticsDTO> getOrderStatistics(Long productId) throws IOException {
        String endpoint = BASE_URL + "/orders/statistics";

        if (productId != null) {
            endpoint += "?productId=" + productId;
        }

        HttpURLConnection conn = (HttpURLConnection)
                new URL(endpoint).openConnection();

        conn.setRequestMethod("GET");

        OrderStatisticsDTO[] arr =
                mapper.readValue(conn.getInputStream(), OrderStatisticsDTO[].class);

        return Arrays.asList(arr);
    }
}
