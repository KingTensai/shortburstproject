import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class BackendService {

    private static final String BASE_URL = "http://localhost:8080"; // your backend URL
    private final ObjectMapper mapper = new ObjectMapper();

    public List<OrderStatisticsDTO> getOrderStatistics() throws IOException {
        URL url = new URL(BASE_URL + "/orders/statistics");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return Arrays.asList(mapper.readValue(conn.getInputStream(), OrderStatisticsDTO[].class));
    }

    public List<ProductSummaryDTO> getProductStatistics() throws IOException {
        URL url = new URL(BASE_URL + "/products/statistics");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return Arrays.asList(mapper.readValue(conn.getInputStream(), ProductSummaryDTO[].class));
    }
}
