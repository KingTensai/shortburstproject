import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductStatisticsPage extends WebPage {

    private final BackendService backendService = new BackendService();

    public ProductStatisticsPage() {
        add(new FeedbackPanel("feedback"));

        try {
            List<ProductSummaryDTO> stats = backendService.getProductStatistics();

            String labels = stats.stream()
                    .map(s -> "\"" + s.name + "\"")
                    .collect(Collectors.joining(", "));

            String quantities = stats.stream()
                    .map(s -> String.valueOf(s.stock))
                    .collect(Collectors.joining(", "));

            add(new org.apache.wicket.markup.html.basic.Label("chartLabels", labels).setEscapeModelStrings(false));
            add(new org.apache.wicket.markup.html.basic.Label("chartData", quantities).setEscapeModelStrings(false));

        } catch (Exception e) {
            error("Failed to load product statistics: " + e.getMessage());
        }
    }
}