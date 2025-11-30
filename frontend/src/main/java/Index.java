import org.apache.wicket.markup.html.basic.Label;

public class Index extends BasePage {

    public Index() {
        // Welcome message
        body.add(new Label("welcomeMessage", "Welcome to Shortburst 2025 Dashboard!"));
    }

    @Override
    protected String getPageTitle() {
        return "Dashboard";
    }
}
