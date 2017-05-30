package steps;

import app_context.properties.G4Properties;
import controllers.APILogin;
import errors.NullReturnException;
import java.util.List;
import model.User;
import model.entities.Entities;
import org.apache.log4j.Logger;
import users_management.StorageUsersManager;
import zapi.ZAPIService;

public class GlobalSteps {

    static Logger log = Logger.getLogger(GlobalSteps.class);
    private static final String ADMIN_ROLE = "admin";

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

    public static User getUserByRole(String role) {
        try {
            return Entities.getUsers().getEntity(role);
        } catch (NullReturnException e) {
            log.error("Cannot find user by given role:" + role);
            return null;
        }
    }

    @Deprecated
    public static User getUserWithPermissions(String... permissions) {
        StorageUsersManager manager = StorageUsersManager.getManager();
        log.debug("Finding users with permissions: " + permissions.toString());
        List<User> users = manager.getUsersWithPermissions(permissions);
        if (users.size() == 0) {
            log.debug("Users are not found. Creating new user with required permissions");
            APILogin loginController = new APILogin();
            loginController.signInAsUser(getUserByRole(ADMIN_ROLE));
            User user = UserSteps.createUserWithPermissions(permissions);
            manager.addUser(user, permissions);
            log.debug("User is created");
            return user;
        }
        return users.get(0);
    }

}
