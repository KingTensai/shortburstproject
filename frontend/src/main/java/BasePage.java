import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

public abstract class BasePage extends WebPage {

    protected WebMarkupContainer body;

    public BasePage() {
        body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        add(body);
        add(new BookmarkablePageLink<>("orderStatsLink", OrderStatisticsPage.class));
        add(new BookmarkablePageLink<>("productStatsLink", ProductStatisticsPage.class));
        add(new Label("pageTitle", Model.of("Home")));

    }

    protected abstract String getPageTitle();

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forReference(
                new PackageResourceReference(BasePage.class, "css/bootstrap.min.css")
        ));

        response.render(JavaScriptHeaderItem.forReference(
                new PackageResourceReference(BasePage.class, "js/bootstrap.bundle.min.js")
        ));
    }
}
