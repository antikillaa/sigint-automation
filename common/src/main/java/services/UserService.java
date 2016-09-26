package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.UserRequest;
import json.JsonCoverter;
import model.User;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;


public class UserService implements EntityService<User> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    Logger log = Logger.getLogger(UserService.class);

    /**
     * Add new G4 User.
     *
     * @param entity New user
     * @return Response status code
     */
    public int add(User entity) {
        log.info("Creating new user");
        log.debug(Parser.entityToString(entity));

        UserRequest request = new UserRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

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
     * Get current G4 user.
     * API: GET "/api/auth/users/me"
     *
     * @return current G4 user
     */
    public User me() {
        log.info("Get current user...");
        UserRequest request = new UserRequest().me();
        G4Response response = g4HttpClient.sendRequest(request);

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
     * Get report role for G4 User.
     *
     * @param user G4 user
     * @return 'Report role' for G4 user
     * <br>note: ADMIN user has 'approver' role
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
