package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.UserRequest;
import model.RequestResult;
import model.User;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;


public class UserService implements EntityService<User> {

    private Logger log = Logger.getLogger(UserService.class);

    /**
     * Add new G4 User.
     *
     * @param entity New user
     * @return Response status code
     */
    public OperationResult<User> add(User entity) {
        log.info("Creating new user");
        log.debug(Parser.entityToString(entity));

        UserRequest request = new UserRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            Entities.getUsers().addOrUpdateEntity(operationResult.getResult());
        }
        return operationResult;
    }

    public OperationResult<RequestResult> remove(User entity) {
        log.info("Deleting user, id:" + entity.getId() + " name:" + entity.getName());

        UserRequest request = new UserRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<RequestResult> operationResult = new OperationResult<>(response, RequestResult.class);
        if (operationResult.isSuccess()) {
            Entities.getUsers().removeEntity(entity);
        }
        return operationResult;
    }

    public OperationResult<EntityList<User>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    public OperationResult<EntityList<User>> list() {
        log.info("Getting users list");

        UserRequest request = new UserRequest().list();
        G4Response response = g4HttpClient.sendRequest(request);

        List<User> users = JsonConverter.readEntitiesFromResponse(response, User[].class);
        return new OperationResult<>(response, new EntityList<>(users));
    }

    public OperationResult<User> update(User entity) {
        log.info("Update user id:" + entity.getId() + " name:" + entity.getName());

        UserRequest request = new UserRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            Entities.getUsers().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    public OperationResult<User> view(String id) {
        throw new NotImplementedException();
    }

    /**
     * Get current G4 user.
     * API: GET "/api/auth/users/me"
     *
     * @return current G4 user
     */
    public OperationResult<User> me() {
        log.info("Get current user...");
        UserRequest request = new UserRequest().me();
        G4Response response = g4HttpClient.sendRequest(request);
        return new OperationResult<>(response, User.class);
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
