package services;

import data_for_entity.RandomEntities;
import data_for_entity.data_providers.user_password.UserPasswordProvider;
import error_reporter.ErrorReporter;
import http.G4Response;
import http.OperationResult;
import http.requests.ChangePasswordRequest;
import http.requests.UserRequest;
import json.JsonConverter;
import model.*;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.*;

public class UserService implements EntityService<User> {

    private static final Logger log = Logger.getLogger(UserService.class);
    private static final UserRequest request = new UserRequest();
    private static final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
    private static String defaultTeamId;
    private static RandomEntities randomEntities = new RandomEntities();
    private static UserService userService = new UserService();
    private static ResponsibilityService responsibilityService = new ResponsibilityService();
    private static TitleService titleService = new TitleService();

    private static final int PASSWORD_LENGTH = 10;

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
        if (entity.getCreatedBy() == null) {
            ErrorReporter.raiseError("You are trying to delete system user " + entity.getName());
        }

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
        log.info("Search users by filter: " + filter);

        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<User[]> operationResult = new OperationResult<>(response, User[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    @Override
    public OperationResult<List<User>> list() {
        throw new NotImplementedException();
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
        List<String> roles = user.getEffectivePermission().getActions();
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

    public static User createUserWithPermissions(String... permissions) {
        Responsibility responsibility = randomEntities.randomEntity(Responsibility.class);
        responsibility.setPermissions(Arrays.asList(permissions));
        OperationResult<Responsibility> responsibilityOperationResult = responsibilityService.add(responsibility);
        if (responsibilityOperationResult.isSuccess()) {
            Title title = randomEntities.randomEntity(Title.class);
            title.setResponsibilities(Collections.singletonList(responsibilityOperationResult.getEntity().getId()));
            OperationResult<Title> titleOperationResult = titleService.add(title);
            if (titleOperationResult.isSuccess()) {
                User newUser = randomEntities.randomEntity(User.class);
                newUser.setParentTeamId("00"); // default Team
                newUser.getDefaultPermission().setTitles(Collections.singletonList(titleOperationResult.getEntity().getId()));
                OperationResult<User> userOperationResult = userService.add(newUser);
                if (userOperationResult.isSuccess()) {
                    User user = userOperationResult.getEntity();
                    user.setNewPassword(new UserPasswordProvider().generate(PASSWORD_LENGTH));
                    OperationResult<AuthResponseResult> firstPasswordChangeResult = userService.changeTempPassword(user);
                    if (firstPasswordChangeResult.isSuccess()) {
                        return user;
                    } else {
                        throw new AssertionError("Unable change password for new User: " + JsonConverter.toJsonString(newUser));
                    }
                } else {
                    throw new AssertionError("Unable create User: " + JsonConverter.toJsonString(newUser));
                }
            } else {
                throw new AssertionError("Unable create Title: " + JsonConverter.toJsonString(title));
            }
        } else {
            throw new AssertionError("Unable create Responsibility: " + JsonConverter.toJsonString(responsibility));
        }
    }

    public List<OperationResult> removeAll() {
        List<OperationResult> operationResults = new ArrayList<>();

        Long count = new ArrayList<>(Entities.getUsers().getEntities()).stream()
                .filter(user -> user.getCreatedBy() != null)
                .peek(user -> operationResults.add(remove(user)))
                .count();

        return operationResults;
    }
}
