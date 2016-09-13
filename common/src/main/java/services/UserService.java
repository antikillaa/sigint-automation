package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.UserRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.PegasusMediaType;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import utils.Parser;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.List;


public class UserService implements EntityService<User> {

    private static RsClient rsClient = new RsClient();
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(UserService.class);
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();
    private UserRequest request = new UserRequest();

    /**
     * Add new User.
     * @param entity New user
     * @return Response status code
     */
    public int add(User entity) {
        log.info("Creating new user");
        log.debug(Parser.entityToString(entity));

        G4Response response = g4Client.post(
                sigintHost + request.getURI(),
                entity,
                request.getCookie(),
                PegasusMediaType.PEGASUS_JSON
        );

        User createdUser = JsonCoverter.readEntityFromResponse(response, User.class);
        if (createdUser != null) {
            Entities.getUsers().addOrUpdateEntity(createdUser.setPassword(entity.getPassword()));
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

    /**
     * Get current user.
     * @return current user
     */
    public User me(Token token) {
        log.info("Get current user...");
        Response response = rsClient.get(
                sigintHost + request.me().getURI(),
                new Cookie("t", token.getValue()),
                PegasusMediaType.PEGASUS_JSON
        );
        String url = sigintHost + request.me().getURI();
        G4Response response = g4Client.get(url, request.getCookie(), PegasusMediaType.PEGASUS_JSON);

        User user = JsonCoverter.readEntityFromResponse(response, User.class);
        if (user != null) {
            return user;
        } else {
            String errorMessage = "Unable to get current user";
            log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
    }

    /**
     * Get report role for User.
     * @param user User
     * @return 'Report role' for user
     * <br>ADMIN user has 'approver' role
     */
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
