import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;

import java.util.List;
import java.util.stream.Collectors;

public class ProductStatisticsPage extends BasePage {
    @Override
    protected String getPageTitle() {
        return "Product Statistics";
    }

    public ProductStatisticsPage() {
        add(new FeedbackPanel("feedback"));

        try {
            BackendService backendService = new BackendService();
            List<ProductSummaryDTO> stats = backendService.getProductStatistics();

            String labels = stats.stream()
                    .map(s -> "\"" + s.getName() + "\"")
                    .collect(Collectors.joining(", "));

            String quantities = stats.stream()
                    .map(s -> String.valueOf(s.getStock()))
                    .collect(Collectors.joining(", "));

            add(new Label("chartLabels", labels).setEscapeModelStrings(false));
            add(new Label("chartData", quantities).setEscapeModelStrings(false));

        } catch (Exception e) {
            error("Failed to load product statistics: " + e.getMessage());
        }
    }
}
