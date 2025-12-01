import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderStatisticsPage extends BasePage {

    private final BackendService backendService = new BackendService();

    public OrderStatisticsPage() {
        super();
        addOrReplace(new Label("pageTitle", getPageTitle()));
        WebMarkupContainer body = (WebMarkupContainer) get("body");
        body.add(new FeedbackPanel("feedback"));

        List<ProductSummaryDTO> products;
        try {
            products = backendService.getProductStatistics();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IModel<ProductSummaryDTO> selectedProduct = new Model<>(null);

        DropDownChoice<ProductSummaryDTO> productChoice = new DropDownChoice<>(
                "productFilter",
                selectedProduct,
                products,
                new ChoiceRenderer<>("name", "productId")
        );
        productChoice.setNullValid(true);

        body.add(productChoice);

        Label chartLabels = new Label("chartLabels", Model.of(""));
        Label chartData = new Label("chartData", Model.of(""));
        chartLabels.setOutputMarkupId(true);
        chartData.setOutputMarkupId(true);
        body.add(chartLabels);
        body.add(chartData);

        productChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                loadChartData(selectedProduct.getObject(), chartLabels, chartData, target);
            }
        });

        loadChartData(null, chartLabels, chartData, null);
    }

    private void loadChartData(ProductSummaryDTO product,
                               Label labels,
                               Label data,
                               AjaxRequestTarget target) {

        try {
            List<OrderStatisticsDTO> orders =
                    backendService.getOrderStatistics(product != null ? product.getProductId() : null);

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
