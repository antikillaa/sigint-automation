package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.UserRequest;
import json.JsonCoverter;
import model.AppContext;
import model.PegasusMediaType;
import model.User;
import org.apache.log4j.Logger;

import java.util.List;


public class UserService implements EntityService<User> {

    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(UserService.class);
    private final String sigintHost = context.environment().getSigintHost();
    private UserRequest request = new UserRequest();

    public int add(User entity) {
        log.info("Creating new user");
        try {
            log.debug("User: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.info(e.getMessage());
        }

        G4Response response = g4Client.post(
                sigintHost + request.getURI(),
                entity,
                request.getCookie(),
                PegasusMediaType.PEGASUS_JSON
        );

        User createdUser = JsonCoverter.readEntityFromResponse(response, User.class);
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
        log.info("Get current user...");
        String url = sigintHost + request.me().getURI();
        G4Response response = g4Client.get(url, request.getCookie(), PegasusMediaType.PEGASUS_JSON);

        User user = JsonCoverter.readEntityFromResponse(response, User.class);
        if (user != null) {
            context.entities().getUsers().addOrUpdateEntity(user);
            context.setLoggedUser(user);
        }
        return user;
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
