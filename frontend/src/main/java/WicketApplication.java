import org.apache.wicket.*;
import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    @Override
    public Class<? extends org.apache.wicket.Page> getHomePage() {
        return Index.class;
    }

    @Override
    public void init() {
        super.init();

        getMarkupSettings().setStripWicketTags(false);
    }
}
