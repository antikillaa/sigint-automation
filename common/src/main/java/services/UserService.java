package services;

import data_for_entity.RandomEntities;
import error_reporter.ErrorReporter;
import http.G4Response;
import http.OperationResult;
import http.requests.PasswordsRequest;
import http.requests.UserRequest;
import json.JsonConverter;
import model.*;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class UserService implements EntityService<User> {

    private static final Logger log = Logger.getLogger(UserService.class);
    private static final UserRequest request = new UserRequest();
    private static final PasswordsRequest passwordRequest = new PasswordsRequest();
    private static String defaultTeamId;
    private static RandomEntities randomEntities = new RandomEntities();
    private static UserService userService = new UserService();
    private static ResponsibilityService responsibilityService = new ResponsibilityService();
    private static TitleService titleService = new TitleService();

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

    public OperationResult<AuthResponseResult> firstPasswordChange(User user, String newPassword) {
        log.info("New password for " + user.getName() + " user: " + newPassword);

        UserPassword userPassword = new UserPassword(user, newPassword);

        G4Response response = g4HttpClient.sendRequest(passwordRequest.create(userPassword));
        OperationResult<AuthResponseResult> operationResult =
                new OperationResult<>(response, AuthResponseResult.class);

        if (operationResult.isSuccess()) {
            user.setPassword(newPassword);
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
            title.setResponsibilities(Arrays.asList(responsibilityOperationResult.getEntity().getId()));
            OperationResult<Title> titleOperationResult = titleService.add(title);
            if (titleOperationResult.isSuccess()) {
                User newUser = randomEntities.randomEntity(User.class);
                newUser.setParentTeamId("00"); // default Team
                //newUser.getDefaultPermission().getRecord().setClearances(Arrays.asList("TS-SCI","C","OUO","S","TS","TS-CIO","TS-OS"));
                //newUser.getDefaultPermission().getRecord().setDataSources(Arrays.asList("NEWS","CIO","ETISALAT","F","FLASHPOINT","FORUM","GOVINT","INSTAGRAM","KARMA","ODD JOBS","OSINT","S","SIGINT","SITA","T","TWITTER","UDB","YOUTUBE"));
                //-----
                newUser.getDefaultPermission().setTitles(Arrays.asList(titleOperationResult.getEntity().getId()));
                OperationResult<User> userOperationResult = userService.add(newUser);
                if (userOperationResult.isSuccess()) {
                    User user = userOperationResult.getEntity();
                    String newPassword = RandomStringUtils.randomAlphanumeric(10);
                    OperationResult<AuthResponseResult> firstPasswordChangeResult = userService.firstPasswordChange(user, newPassword);
                    if (firstPasswordChangeResult.isSuccess()) {
                        user.setPassword(newPassword);
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
}
