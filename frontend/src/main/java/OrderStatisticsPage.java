import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import java.util.List;
import java.util.stream.Collectors;

public class OrderStatisticsPage extends WebPage {

    private final BackendService backendService = new BackendService();

    public OrderStatisticsPage() {
        add(new FeedbackPanel("feedback"));

        try {
            List<OrderStatisticsDTO> stats = backendService.getOrderStatistics();

            // Convert data into JSON-friendly strings for JS
            String labels = stats.stream()
                    .map(s -> "\"" + s.location + " (#" + s.orderId + ")\"")
                    .collect(Collectors.joining(", "));

            String totals = stats.stream()
                    .map(s -> String.valueOf(s.totalPrice))
                    .collect(Collectors.joining(", "));

            add(new org.apache.wicket.markup.html.basic.Label("chartLabels", labels).setEscapeModelStrings(false));
            add(new org.apache.wicket.markup.html.basic.Label("chartData", totals).setEscapeModelStrings(false));

        } catch (Exception e) {
            error("Failed to load order statistics: " + e.getMessage());
        }
    }
}