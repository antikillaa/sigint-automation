package steps;

import app_context.entities.Entities;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import zapi.ZAPIService;

public class GlobalSteps {

    static Logger log = Logger.getLogger(GlobalSteps.class);
    

    
    @AfterStories
    public void reportResults(){
        ZAPIService service = new ZAPIService();
        Boolean shouldReport = G4Properties.getRunProperties().shouldReport();
        try {
        if (shouldReport) {
            service.reportToZephyr();
        }
         }catch (Exception e){
            log.error(e.getMessage());
            log.trace(e.getMessage(), e);
        }
        }

    

    public static User getUserByRole(String role) {
        try {
            return Entities.getUsers().getEntity(role);
        } catch (NullReturnException e) {
            log.error("Cannot find user by given role:"+role);
            return null;
        }
    
    }
}
