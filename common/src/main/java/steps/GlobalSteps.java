package steps;

import errors.NullReturnException;
import model.AppContext;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import rs.client.JsonCoverter;
import zapi.ReportParser;

import java.io.InputStream;

public class GlobalSteps {

    static Logger log = Logger.getRootLogger();
    static AppContext context = AppContext.getContext();


    @BeforeStories
    public void initEntities() {
        log.debug("Start loading pre-defined set of users");
        UsersList users = new UsersList();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream users_input = classloader.getResourceAsStream("default_users.json");
        try {
            users = JsonCoverter.fromJsonToObjectsList(users_input, UsersList.class);
        } catch (NullReturnException e) {
            log.warn("Entities weren't loaded from config file");
            e.printStackTrace();
        }
        AppContext context = AppContext.getContext();
        context.registerList(users);
        RFIList rfiList = new RFIList();
        context.registerList(rfiList);

    }

    @AfterStories
    public void reportToZephyr(){
        new ReportParser().reportToZephyr("target/allure-results");
    }

    public static User getUserByRole(String role) {
        User user;
        try {
            user = context.getEntitiesList(UsersList.class).getEntity(role);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("User doesn't exist");
        }
        return user;
    }
}
