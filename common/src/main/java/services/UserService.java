package services;

import http.G4Response;
import http.OperationResult;
import http.requests.ChangePasswordRequest;
import http.requests.UserRequest;
import java.util.ArrayList;
import java.util.List;
import json.JsonConverter;
import model.AuthResponseResult;
import model.RequestResult;
import model.SearchFilter;
import model.User;
import model.UserPassword;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class UserService implements EntityService<User> {

    private static final Logger log = Logger.getLogger(UserService.class);
    private static final UserRequest request = new UserRequest();
    private static final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
    private static String defaultTeamId;

    public static String getDefaultTeamId() {
        return defaultTeamId;
    }

    public static void setDefaultTeamId(String defaultTeamId) {
        UserService.defaultTeamId = defaultTeamId;
    }

    /**
     * Add new G4 User.
     *
     * @param entity New user
     * @return Response status code
     */
    @Override
    public OperationResult<User> add(User entity) {
        log.info("Creating new user");

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            User user = operationResult.getEntity();
            if (entity.getPassword() != null) {
                user.setPassword(entity.getPassword());
            }
            Entities.getUsers().addOrUpdateEntity(user);
            Entities.getOrganizations().addOrUpdateEntity(user);
        } else {
            log.error("Can't create User " + entity.toString());
        }
        return operationResult;
    }

    @Override
    public OperationResult<RequestResult> remove(User entity) {
        log.info("Deleting user, id:" + entity.getId() + " name:" + entity.getName());

        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<RequestResult> operationResult = new OperationResult<>(response, RequestResult.class);
        if (operationResult.isSuccess()) {
            Entities.getUsers().removeEntity(entity);
            Entities.getOrganizations().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<User>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<User>> list() {
        log.info("Getting users list");

        G4Response response = g4HttpClient.sendRequest(request.list());

        List<User> users = JsonConverter.jsonToObjectsList(response.getMessage(), User[].class);
        return new OperationResult<>(response, users);
    }

    @Override
    public OperationResult<User> update(User entity) {
        log.info("Update user id:" + entity.getId() + " name:" + entity.getName());

        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            User user = operationResult.getEntity();
            if (entity.getNewPassword() != null) {
                user.setPassword(entity.getNewPassword());
            }
            Entities.getUsers().addOrUpdateEntity(user);
            Entities.getOrganizations().addOrUpdateEntity(user);
        }
        return operationResult;
    }

    @Override
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

        G4Response response = g4HttpClient.sendRequest(request.me());

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            Entities.getUsers().addOrUpdateEntity(operationResult.getEntity());
            Entities.getOrganizations().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<AuthResponseResult> changePassword(User user) {
        log.info(String.format("Changing %s's password from %s to %s",
            user.getName(), user.getPassword(), user.getNewPassword()));

        UserPassword userPassword = new UserPassword(user);

        G4Response response = g4HttpClient.sendRequest(changePasswordRequest.updatePassword(userPassword));
        return processPasswordResponse(user, response);
    }

    public OperationResult<AuthResponseResult> changeTempPassword(User user) {
        log.info(String.format("Setting first password for %s user: %s",
            user.getName(), user.getNewPassword()));

        UserPassword userPassword = new UserPassword(user);

        G4Response response = g4HttpClient.sendRequest(changePasswordRequest.updateTempPassword(userPassword));
        return processPasswordResponse(user, response);
    }

    private static OperationResult<AuthResponseResult> processPasswordResponse(User user, G4Response response) {

        OperationResult<AuthResponseResult> operationResult =
            new OperationResult<>(response, AuthResponseResult.class);

        if (operationResult.isSuccess())  {
            user.setPassword(user.getNewPassword());
            Entities.getUsers().addOrUpdateEntity(user);
            Entities.getOrganizations().addOrUpdateEntity(user);
        }
        return operationResult;
    }

    /**
     * Get report role for G4 User.
     *
     * @param user G4 user
     * @return 'Report role' for G4 user
     * <br>note: ADMIN user has 'approver' role
     */
    String getReportRole(User user) {
        ArrayList<String> roles = user.getEffectivePermission().getActions();
        if (roles.contains("REPORT_UPDATE_APPROVER")) {
            return "approver";
        } else if (roles.contains("REPORT_UPDATE_ANALYST")) {
            return "analyst";
        } else if (roles.contains("REPORT_UPDATE")) {
            return "operator";
        } else {
            log.error("Can not get reportRole for current user");
            throw new AssertionError("Can not get reportRole for current user");
        }
    }

}
