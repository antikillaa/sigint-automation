package steps;

import abs.EntityList;
import app_context.entities.Entities;
import app_context.entities.UsersList;
import controllers.APILogin;
import errors.NullReturnException;
import http.requests.GetDictionariesRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Dictionary;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import zapi.ZAPIService;

import javax.ws.rs.core.Response;
import java.io.InputStream;

public class GlobalSteps {

    static Logger log = Logger.getLogger(GlobalSteps.class);
    static AppContext context = AppContext.getContext();

    @BeforeStories
    public void beforeStories() {
        initEntities();
        initDictionary();
    }

    public void setUserToContext(String role) {
        log.info("Getting user with role " + role);
        User user = getUserByRole(role);
        context.put("user", user);
    }


    private void initEntities() {
        log.info("Start loading pre-defined set of users");
        UsersList users;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream users_input = classloader.getResourceAsStream("default_users.json");
        try {
            users = JsonCoverter.fromJsonToObjectsList(users_input, UsersList.class);
            Entities.get().setUsers(users);
        } catch (NullReturnException e) {
            log.warn("Users weren't loaded from config file");
        }
    }

    private void initDictionary() {
        setUserToContext("admin");
        new APILogin().signInWithCrendentials("valid");
        GetDictionariesRequest request = new GetDictionariesRequest();
        Response response = new RsClient().get(context.environment().getSigintHost()+ request.getURI(),
                request.getCookie());
        Dictionary dictionary = JsonCoverter.readEntityFromResponse(response, Dictionary.class, "result");
        context.setDictionary(dictionary);
    }

    @AfterStories
    public void reportResults(){
        ZAPIService service = new ZAPIService();
        Boolean shouldReport = Boolean.valueOf(context.getGeneralProperties().getProperty("report"));
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
        User user;
        try {
            user = context.entities().getUsers().getEntity(role);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("User doesn't exist");
        }
        return user;
    }
}
