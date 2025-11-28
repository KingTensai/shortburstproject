import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.ChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class OrderStatisticsPage extends BasePage {

    @Inject
    BackendService backendService; // Calls your REST backend

    public OrderStatisticsPage() {
        // Feedback panel
        add(new FeedbackPanel("feedback"));

        // Dropdown to select product
        List<ProductSummaryDTO> products = backendService.getProductStatistics();
        IModel<ProductSummaryDTO> selectedProduct = new Model<>(null);
        DropDownChoice<ProductSummaryDTO> productChoice = new DropDownChoice<>(
                "productFilter",
                selectedProduct,
                products,
                new ChoiceRenderer<>("name", "productId")
        );
        productChoice.setNullValid(true);
        add(productChoice);

        // Chart data spans
        Label chartLabels = new Label("chartLabels", Model.of(""));
        Label chartData = new Label("chartData", Model.of(""));
        chartLabels.setOutputMarkupId(true);
        chartData.setOutputMarkupId(true);
        add(chartLabels);
        add(chartData);

        // Update chart when dropdown changes
        productChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                loadChartData(selectedProduct.getObject(), chartLabels, chartData, target);
            }
        });

        // Load initial chart with all orders
        loadChartData(null, chartLabels, chartData, null);
    }

    private void loadChartData(ProductSummaryDTO product, Label labels, Label data, AjaxRequestTarget target) {
        try {
            List<OrderStatisticsDTO> orders = backendService.getOrderStatistics(product != null ? product.productId : null);

            // Prepare chart labels (dates) and data (count)
            Map<String, Long> ordersPerLocation = orders.stream()
                    .collect(Collectors.groupingBy(OrderStatisticsDTO::getLocation, Collectors.counting()));

            String labelStr = ordersPerLocation.keySet().stream()
                    .map(s -> "'" + s + "'")
                    .collect(Collectors.joining(","));
            String dataStr = ordersPerLocation.values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            labels.setDefaultModelObject(labelStr);
            data.setDefaultModelObject(dataStr);

            if (target != null) {
                target.add(labels);
                target.add(data);
                target.appendJavaScript("updateOrderChart();");
            }
        } catch (Exception e) {
            error("Failed to load chart data: " + e.getMessage());
        }
    }

    @Override
    protected String getPageTitle() {
        return "Order Statistics";
    }
}
