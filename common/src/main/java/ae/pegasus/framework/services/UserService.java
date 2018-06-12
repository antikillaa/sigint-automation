package ae.pegasus.framework.services;

import ae.pegasus.framework.data_for_entity.data_providers.user_password.UserPasswordProvider;
import ae.pegasus.framework.error_reporter.ErrorReporter;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.ChangePasswordRequest;
import ae.pegasus.framework.http.requests.UserRequest;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.*;

import static ae.pegasus.framework.json.JsonConverter.toJsonString;
import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemFromList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserService implements EntityService<User> {

    private static final Logger log = Logger.getLogger(UserService.class);
    private static final UserRequest request = new UserRequest();
    private static final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
    private static String defaultTeamId;
    private static Map<String, List<User>> permissionUserMap = new HashMap<>();

    private static TitleService titleService = new TitleService();
    private static ResponsibilityService responsibilityService = new ResponsibilityService();

    private static final int PASSWORD_LENGTH = 10;

    public static String getDefaultTeamId() {
        if (defaultTeamId == null) {
            defaultTeamId = new OrganizationService().getOrCreateByName("QE auto team").getId();
        }
        return defaultTeamId;
    }

    public static void setDefaultTeamId(String defaultTeamId) {
        UserService.defaultTeamId = defaultTeamId;
    }

    @Override
    public OperationResult<User> add(User entity) {
        log.info("Creating new user: " + toJsonString(entity));

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
            log.error("Response:" + response.getMessage());
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
            permissionUserMap.values()
                    .forEach(users -> users
                            .removeIf(user -> user.getName().equals(entity.getName())));
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
        throw new NotImplementedException("");
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
                user.setNewPassword(null);
            }
            Entities.getUsers().addOrUpdateEntity(user);
            Entities.getOrganizations().addOrUpdateEntity(user);
        }
        return operationResult;
    }

    @Override
    public OperationResult<User> view(String id) {
        throw new NotImplementedException("");
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
            user.setNewPassword(null);
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
        List<String> roles = user.getDefaultPermission().getActions();
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
        User user = User.newBuilder()
                .randomUser()
                .withPermission(permissions)
                .withAllClassification()
                .withAllSources()
                .build();

        return createUserWithPermissions(user);
    }

    public User createUserWithPermissions(User user) {
        // create permissions
        List<String> permissions = user.getDefaultPermission().getActions();
        Responsibility responsibility = responsibilityService.createWithPermissions(permissions.toArray(new String[0]));
        Title title = titleService.createWithResponsibility(responsibility);

        // update orgUnits
        user = addOrgUnitWithTitles(user, getDefaultTeamId(), Collections.singletonList(title.getId()));

        // create user
        OperationResult<User> userOperationResult = add(user);
        assertTrue("Unable create User: " + toJsonString(user), userOperationResult.isSuccess());
        user = userOperationResult.getEntity();

        // update password
        user.setNewPassword(new UserPasswordProvider().generate(PASSWORD_LENGTH));
        OperationResult<AuthResponseResult> firstPasswordChangeResult = changeTempPassword(user);
        if (!firstPasswordChangeResult.isSuccess()) {
            log.error(firstPasswordChangeResult.getMessage());
            throw new AssertionError("Unable change password for new User: " + toJsonString(user));
        }

        return user;
    }

    public void setPermissions(User user, String... permissions) {
        Responsibility responsibility = responsibilityService.createWithPermissions(permissions);

        // get user title
        List<TeamTitle> teamTitles = user.getDefaultPermission().getTeamTitles();
        assertFalse("Current user without TeamTitles!", teamTitles.isEmpty());

        TeamTitle teamTitle = getRandomItemFromList(teamTitles);
        String titleID = getRandomItemFromList(teamTitle.getTitles());
        Title title = titleService.view(titleID).getEntity();

        // update user title with new responsibilities
        title.setResponsibilities(Collections.singletonList(responsibility.getId()));
        OperationResult<Title> titleOperationResult = titleService.update(title);
        if (!titleOperationResult.isSuccess())
            throw new AssertionError("Unable update Title: " + toJsonString(title));
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

        existingPermissions.forEach(permission -> permissionUserMap.put(permission.getName(), new ArrayList<>()));
    }

    public List<User> getUsersWithPermissions(String... permissions) {
        log.debug("Finding users with permissions: " + Arrays.toString(permissions));
        if (permissionUserMap.isEmpty()) {
            initializeUserMap();
        }
        List<User> users = new ArrayList<>();
        for (String permission : permissions) {
            try {
                if (users.size() == 0) {
                    users.addAll(permissionUserMap.get(permission));
                } else {
                    users.retainAll(permissionUserMap.get(permission));
                    if (users.size() == 0) {
                        log.debug("User with permissions: " + Arrays.toString(permissions) + "not found!");
                        return users;
                    }
                }
            } catch (NullPointerException e) {
                throw new AssertionError("Permission: " + permission + "not found!");
            }
        }
        log.debug("With permissions: " + Arrays.toString(permissions) + " users found: " + users.size());
        return users;
    }

    public User getOrCreateUserWithPermissions(String... permissions) {
        List<User> users = getUsersWithPermissions(permissions);

        User user;
        if (users.size() == 0) {
            user = createUserWithPermissions(permissions);
            for (String permission : permissions) {
                permissionUserMap.get(permission).add(user);
            }
        } else {
            user = getRandomItemFromList(users);
        }
        return user;
    }

    public User addOrgUnitWithTitles(User user, String orgUnitId, List<String> titles) {
        user.getParentTeamIds().add(orgUnitId);
        user.getParentTeams().put(orgUnitId, Collections.singletonList("MEMBER"));

        TeamTitle teamTitle = new TeamTitle();
        teamTitle.setOrgUnitId(orgUnitId);
        teamTitle.setTitles(titles);
        user.getDefaultPermission().getTeamTitles().add(teamTitle);

        return user;
    }
}
