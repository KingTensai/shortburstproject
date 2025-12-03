import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductStatisticsPage extends BasePage {

    private final Label chartLabels;
    private final Label chartData;

    public ProductStatisticsPage() {
        super();
        WebMarkupContainer body = (WebMarkupContainer) get("body");
        body.add(new FeedbackPanel("feedback").setOutputMarkupId(true));

        BackendService backendService = new BackendService();
        List<ProductSummaryDTO> allProducts;
        try {
            allProducts = backendService.getProductStatistics();
        } catch (IOException e) {
            error("Failed to load product statistics: " + e.getMessage());
            allProducts = List.of();
        }

        List<String> productNames = allProducts.stream()
                .map(ProductSummaryDTO::getName)
                .sorted()
                .toList();

        IModel<String> selectedProduct = new Model<>(null);

        DropDownChoice<String> productFilter = new DropDownChoice<>(
                "productFilter",
                selectedProduct,
                productNames
        );
        productFilter.setNullValid(true);
        productFilter.setOutputMarkupId(true);
        body.add(productFilter);

        chartLabels = new Label("chartLabels", "");
        chartLabels.setEscapeModelStrings(false);
        chartLabels.setOutputMarkupId(true);
        body.add(chartLabels);

        chartData = new Label("chartData", "");
        chartData.setEscapeModelStrings(false);
        chartData.setOutputMarkupId(true);
        body.add(chartData);

        updateChart(allProducts, selectedProduct.getObject(), null);

        final List<ProductSummaryDTO> allProductsFinal = allProducts;
        productFilter.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                updateChart(allProductsFinal, selectedProduct.getObject(), target);
            }
        });
    }

    @Override
    protected String getPageTitle() {
        return "Product Statistics";
    }

    private void updateChart(List<ProductSummaryDTO> allProducts, String selectedProductName, AjaxRequestTarget target) {
        List<ProductSummaryDTO> filtered = allProducts;
        if (selectedProductName != null) {
            filtered = allProducts.stream()
                    .filter(p -> selectedProductName.equals(p.getName()))
                    .toList();
        }

        String labels = filtered.stream()
                .map(p -> "\"" + p.getName() + "\"")
                .collect(Collectors.joining(","));

        String salesData = filtered.stream()
                .map(p -> String.valueOf(p.getStock()))
                .collect(Collectors.joining(","));

        chartLabels.setDefaultModelObject(labels);
        chartData.setDefaultModelObject(salesData);

        if (target != null) {
            target.add(chartLabels);
            target.add(chartData);
            target.appendJavaScript(
                    "updateProductChart('" + chartLabels.getMarkupId() + "','" + chartData.getMarkupId() + "');"
            );
        }
    }
}
