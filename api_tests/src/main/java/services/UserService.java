package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.UserRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.PegasusMediaType;
import model.User;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;
import java.util.List;


public class UserService implements EntityService<User> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(UserService.class);
    private final String sigintHost = context.environment().getSigintHost();

    public int add(User entity) {
        log.info("Creating new user");
        try {
            log.info("User: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.info(e.getMessage());
        }

        UserRequest request = new UserRequest();
        Response response = rsClient
                .post(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);
        String jsonString = response.readEntity(String.class);
        log.info("Response: " + jsonString);

        User createdUser = JsonCoverter.fromJsonToObject(jsonString, User.class);
        if (createdUser != null) {
            context.entities().getUsers().addOrUpdateEntity(createdUser.setPassword(entity.getPassword()));
        }
        return response.getStatus();
    }

    public int remove(User entity) {
        return 0;
    }

    public EntityList<User> list(SearchFilter filter) {
        return null;
    }

    public int update(User entity) {
        return 0;
    }

    public User view(String id) {
        return null;
    }

    public User me() {
        log.info("Get current user");
        UserRequest request = new UserRequest().me();
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie(), PegasusMediaType.PEGASUS_JSON);
        context.put("code", response.getStatus());

        String jsonString = response.readEntity(String.class);
        log.info("Response: " + jsonString);

        return JsonCoverter.fromJsonToObject(jsonString, User.class);
    }

    public String getReportRole(User user) {
        List<String> roles = user.getExpandedRoles();
        if (roles.contains("APPROVER") || roles.contains("ADMIN")) {
            return "approver";
        } else if (roles.contains("ANALYST")) {
            return "analyst";
        } else if (roles.contains("OPERATOR")) {
            return "operator";
        } else {
            log.error("Can not get reportRole for current user");
            throw new AssertionError("Can not get reportRole for current user");
        }
    }

}
