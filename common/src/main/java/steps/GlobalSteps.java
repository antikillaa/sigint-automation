package steps;

import app_context.properties.G4Properties;
import org.apache.log4j.Logger;
import zapi.ZAPIService;

public class GlobalSteps {

    static Logger log = Logger.getLogger(GlobalSteps.class);

//    @AfterStories
    public void reportResults() {
        ZAPIService service = new ZAPIService();
        Boolean shouldReport = G4Properties.getRunProperties().shouldReport();
        try {
            if (shouldReport) {
                service.reportToZephyr();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
