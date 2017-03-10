package steps;

import app_context.entities.Entities;
import app_context.properties.G4Properties;
import data_for_entity.RandomEntities;
import errors.NullReturnException;
import model.Group;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import services.UserService;
import zapi.ZAPIService;

import java.util.Arrays;

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
        } catch (Exception e){
            log.error(e.getMessage(), e);
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


    public static User createUserWithPermissions(String... permissions) {
        Group userGroup = new Group();
        userGroup.setRoles(Arrays.asList(permissions));
        GroupService service = new GroupService();
        service.add(userGroup);
        UserService userService = new UserService();
        RandomEntities entities = new RandomEntities();
        User user  = entities.randomEntity(User.class);
        userService.
    }
}
