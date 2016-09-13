package steps;

import controllers.APILogin;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.GetDictionariesRequest;
import json.JsonCoverter;
import model.AppContext;
import model.Dictionary;
import model.User;
import model.lists.UsersList;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import zapi.ZAPIService;

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
        context.entities().setUsers(users);
    }

    private void initDictionary() {
        setUserToContext("admin");
        new APILogin().signInWithCrendentials("valid");
        GetDictionariesRequest request = new GetDictionariesRequest();

        String url = context.environment().getSigintHost()+ request.getURI();
        G4Response response = new G4Client().get(url, request.getCookie());

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
        } catch (Exception e){
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
