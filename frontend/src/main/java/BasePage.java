import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class BasePage extends WebPage {

    protected WebMarkupContainer body;

    public BasePage() {
        add(new Label("pageTitle", "Home"));
        add(new BookmarkablePageLink<>("orderStatsLink", OrderStatisticsPage.class));
        add(new BookmarkablePageLink<>("productStatsLink", ProductStatisticsPage.class));

        body = new WebMarkupContainer("body");
        body.setOutputMarkupId(true);
        add(body);
    }

    protected abstract String getPageTitle();
}
