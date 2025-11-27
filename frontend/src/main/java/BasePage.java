import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public abstract class BasePage extends WebPage {

    public BasePage() {
        add(new BookmarkablePageLink<>("orderStatsLink", OrderStatisticsPage.class));
        add(new BookmarkablePageLink<>("productStatsLink", ProductStatisticsPage.class));
    }

    protected abstract String getPageTitle();
}