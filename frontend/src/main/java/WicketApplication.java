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
        //IF THIS ISNT HERE ALL JS AND CSS GETS BLOCKED!!!
        getCspSettings().blocking().disabled();
        getCspSettings().reporting().disabled();
        getMarkupSettings().setStripWicketTags(false);
    }
}
