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
    private static Map<String, List<User>> userMap = new HashMap<>();

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

        String adminPassword = appContext.getLoggedUser().getPassword();
        entity.setAdminPassword(adminPassword);

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<User> operationResult = new OperationResult<>(response, User.class);
        if (operationResult.isSuccess()) {
            User user = operationResult.getEntity();
            if (entity.getPassword() != null) {
                user.setPassword(entity.getPassword());
            }
            Entities.getUsers().addOrUpdateEntity(user);
            Entities.getOrganizations().addOrUpdateEntity(user);
            log.info("User is created:" + user.getName());
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

        String adminPassword = appContext.getLoggedUser().getPassword();
        entity.setAdminPassword(adminPassword);

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
        String adminPassword = appContext.getLoggedUser().getPassword();
        String id = appContext.getLoggedUser().getId();

        UserPassword.UserPasswordBuilder builder = new UserPassword.UserPasswordBuilder()
                .newPassword(user.getNewPassword());

        if (Objects.equals(id, user.getId())) {
            log.info(String.format("Changing password for currently logged in %s from %s to %s",
                    user.getName(), user.getPassword(), user.getNewPassword()));
            builder.oldPassword(user.getPassword());
        } else {
            log.info(String.format("Changing %s's password from %s to %s",
                    user.getName(), user.getPassword(), user.getNewPassword()));
            builder.adminPassword(adminPassword);
        }

        G4Response response = g4HttpClient.sendRequest(changePasswordRequest.updatePassword(builder.build(), user.getId()));
        return processPasswordResponse(user, response);
    }

    public OperationResult<AuthResponseResult> changeTempPassword(User user) {
        log.info(String.format("Setting first password for %s user: %s",
                user.getName(), user.getNewPassword()));

        UserPassword userPassword = new UserPassword.UserPasswordBuilder()
                .name(user.getName())
                .newPassword(user.getNewPassword())
                .oldPassword(user.getPassword())
                .build();

        G4Response response = g4HttpClient.sendRequest(changePasswordRequest.updateTempPassword(userPassword));
        return processPasswordResponse(user, response);
    }

    private static OperationResult<AuthResponseResult> processPasswordResponse(User user, G4Response response) {

        OperationResult<AuthResponseResult> operationResult =
                new OperationResult<>(response, AuthResponseResult.class);

        if (operationResult.isSuccess()) {
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
    public String getReportRole(User user) {
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

    public User createUserWithPermissions(String... permissions) {
        log.info("Creating new user with required permissions:" + Arrays.toString(permissions));
        Responsibility responsibility = randomEntities.randomEntity(Responsibility.class);
        responsibility.setPermissions(Arrays.asList(permissions));
        OperationResult<Responsibility> responsibilityOperationResult = new ResponsibilityService().add(responsibility);
        if (!responsibilityOperationResult.isSuccess()) {
            throw new AssertionError("Unable create Responsibility: " + JsonConverter.toJsonString(responsibility));
        }

        Title title = randomEntities.randomEntity(Title.class);
        title.setResponsibilities(Collections.singletonList(responsibilityOperationResult.getEntity().getId()));
        OperationResult<Title> titleOperationResult = new TitleService().add(title);
        if (!titleOperationResult.isSuccess()) {
            throw new AssertionError("Unable create Title: " + JsonConverter.toJsonString(title));
        }

        User newUser = randomEntities.randomEntity(User.class);
        newUser.setParentTeamId("00"); // default Team
        newUser.getDefaultPermission().setTitles(Collections.singletonList(titleOperationResult.getEntity().getId()));

        OperationResult<User> userOperationResult = add(newUser);
        if (!userOperationResult.isSuccess()) {
            throw new AssertionError("Unable create User: " + JsonConverter.toJsonString(newUser));
        }

        User user = userOperationResult.getEntity();
        user.setNewPassword(new UserPasswordProvider().generate(PASSWORD_LENGTH));
        OperationResult<AuthResponseResult> firstPasswordChangeResult = changeTempPassword(user);
        if (!firstPasswordChangeResult.isSuccess()) {
            log.error(firstPasswordChangeResult.getMessage());
            throw new AssertionError("Unable change password for new User: " + JsonConverter.toJsonString(newUser));
        }

        return user;
    }

    public List<OperationResult> removeAll() {
        List<OperationResult> operationResults = new ArrayList<>();

        new ArrayList<>(Entities.getUsers().getEntities()).stream()
                .filter(user -> user.getCreatedBy() != null)
                .forEach(user -> operationResults.add(remove(user)));

        return operationResults;
    }

    private void initializeUserMap() {
        List<Permission> existingPermissions = PermissionService.getPermissions();
        if (existingPermissions.size() == 0) {
            ErrorReporter.raiseError("List of permissions wasn't loaded");
        }

        existingPermissions.forEach(permission -> userMap.put(permission.getName(), new ArrayList<>()));
    }

    public List<User> getUsersWithPermissions(String... permissions) {
        log.debug("Finding users with permissions: " + Arrays.toString(permissions));
        if (userMap.isEmpty()) {
            initializeUserMap();
        }
        List<User> users = new ArrayList<>();
        for (String permission : permissions) {
            if (users.size() == 0) {
                users.addAll(userMap.get(permission));
            } else {
                users.retainAll(userMap.get(permission));
                if (users.size() == 0) {
                    log.debug("User with permissions: " + Arrays.toString(permissions) + "not found!");
                    return users;
                }
            }
        }
        log.trace("With permissions: " + Arrays.toString(permissions) + " users found: " + users.size());
        return users;
    }

    public void addUser(User user, String... permissions) {
        for (String permission : permissions) {
            userMap.get(permission).add(user);
        }
    }
}
