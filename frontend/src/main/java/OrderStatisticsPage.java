import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeMap;
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

        List<OrderStatisticsDTO> orders;
        try {
            orders = backendService.getOrderStatistics();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> locations = orders.stream()
                .map(OrderStatisticsDTO::getLocation)
                .distinct()
                .sorted().toList();


        IModel<String> selectedLocation = new Model<>(null);

        DropDownChoice<String> locationChoice = new DropDownChoice<>(
                "productFilter", // matches wicket:id in HTML
                selectedLocation,
                locations
        );
        locationChoice.setNullValid(true);        // allows "All locations"
        locationChoice.setOutputMarkupId(true);   // needed for AJAX updates
        add(locationChoice);

        body.add(locationChoice);

        Label chartLabels = new Label("chartLabels", Model.of(""));
        Label chartData = new Label("chartData", Model.of(""));

        chartLabels.setOutputMarkupId(true);
        chartData.setOutputMarkupId(true);

        body.add(chartLabels);
        body.add(chartData);

        locationChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String location = selectedLocation.getObject();

                // Filter orders by location if selected
                List<OrderStatisticsDTO> filteredOrders = orders;
                if (location != null) {
                    filteredOrders = orders.stream()
                            .filter(o -> location.equals(o.getLocation()))
                            .toList();
                }

                // Call your chart update method
                loadChartData(filteredOrders, chartLabels, chartData, target);
            }
        });

    }

    private void loadChartData(List<OrderStatisticsDTO> orders,
                               Label labels,
                               Label data,
                               AjaxRequestTarget target) {


        LocalDate fallbackDate = LocalDate.of(2025, 1, 1);

        Map<LocalDate, Double> totalPricePerDay = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getOrderDate() != null ? o.getOrderDate().toLocalDate() : fallbackDate,
                        TreeMap::new,
                        Collectors.summingDouble(OrderStatisticsDTO::getTotalPrice)
                ));

        String labelStr = totalPricePerDay.keySet().stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(","));

        String dataStr = totalPricePerDay.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        labels.setDefaultModelObject(labelStr);
        data.setDefaultModelObject(dataStr);

        if (target != null) {
            target.add(labels);
            target.add(data);
            target.appendJavaScript(
                    "updateOrderChart('" + labels.getMarkupId() + "','" + data.getMarkupId() + "');"
            );
        }
    }


    @Override
    protected String getPageTitle() {
        return "Order Statistics";
    }
}
