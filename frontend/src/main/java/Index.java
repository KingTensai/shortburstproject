import org.apache.wicket.markup.html.basic.Label;

public class Index extends BasePage {

    @Override
    protected String getPageTitle() {
        return "Home";
    }

    public Index() {
        // Welcome message
        add(new Label("welcomeMessage", "Welcome to Shortburst 2025 Dashboard!"));
    }
}