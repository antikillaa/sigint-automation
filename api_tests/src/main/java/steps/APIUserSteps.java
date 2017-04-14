package steps;

import app_context.entities.Entities;
import http.JsonConverter;
import http.OperationResult;
import java.util.ArrayList;
import java.util.List;
import model.RequestResult;
import model.Team;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TeamService;
import services.UserService;

@SuppressWarnings("unchecked")
public class APIUserSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserSteps.class);
    private UserService service = new UserService();
    private TeamService teamService = new TeamService();

    private static final int PASSWORD_LENGTH = 10;

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
    }

    @When("I send create a new user with group request")
    public void createNewUserRequest() {
        List<String> userGroupIds = new ArrayList<>();
        userGroupIds.add(Entities.getGroups().getLatest().getId());
        User user = getRandomUser();
        user.setUserGroupIds(userGroupIds);
        context.put("user", user);
        service.add(user);
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() {
        User createdUser = Entities.getUsers().getLatest();
        User requestUser = context.get("user", User.class);

        log.debug("requested: " + JsonConverter.toJsonString(requestUser));
        log.debug("created: " + JsonConverter.toJsonString(createdUser));

        Assert.assertEquals(createdUser.getName().toLowerCase(), requestUser.getName().toLowerCase());
        Assert.assertEquals(createdUser.getFullName(), requestUser.getFullName());
        Assert.assertEquals(createdUser.getRoles(), requestUser.getRoles());
        Assert.assertEquals(createdUser.getLanguages(), requestUser.getLanguages());
        Assert.assertEquals(createdUser.getStaffId(), requestUser.getStaffId());
        Assert.assertEquals(createdUser.getUserGroupIds(), requestUser.getUserGroupIds());
        Assert.assertEquals(createdUser.getExpandedPermissions(), requestUser.getExpandedPermissions());
        Assert.assertEquals(createdUser.getExpandedRoles(), requestUser.getExpandedRoles());
    }

    static User getRandomUser() {
        return objectInitializer.randomEntity(User.class);
    }

    @When("I send delete user request")
    public void deleteUser() {
        User createdUser = Entities.getUsers().getLatest();
        service.remove(createdUser);
    }

    @When("I send get list of users")
    public void getListOfUsers() {
        OperationResult<List<User>> operationResult = service.list();
        context.put("userList", operationResult.getEntity());
    }

    @Then("Users list size more than $size")
    public void userListMoreThan(String size) {
        List<User> userList = context.get("userList", List.class);

        Assert.assertFalse("Users list is empty", userList.isEmpty());
        Assert.assertTrue(userList.size() > Integer.valueOf(size));
    }

    @Then("delete all users without roles and groups")
    public void cleanupUsers() {
        List<User> userList = context.get("userList", List.class);

        for (User user : userList) {
            if (user.getRoles().isEmpty() && user.getUserGroupIds().isEmpty()) {
                OperationResult<RequestResult> operationResult = service.remove(user);
                Assert.assertEquals("success", operationResult.getEntity().getMessage());
            }
        }
    }

    @When("I send update user request")
    public void updateUser() {
        User user = Entities.getUsers().getLatest();
        User updatedUser = getRandomUser();
        updatedUser.setId(user.getId());
        context.put("user", updatedUser);
        service.update(updatedUser);
    }

    @When("I send get current user request")
    public void getCurrentUserRequest() {
        service.me();
    }

    @When("I send create a new user")
    public void createNewUser() {
        User user = getRandomUser();

        if (UserService.getDefaultTeamId() == null) {
            Team team = objectInitializer.randomEntity(Team.class);
            String teamId = teamService.add(team).getEntity().getId();
            UserService.setDefaultTeamId(teamId);
        }
        user.setParentTeamId(UserService.getDefaultTeamId());

        context.put("user", user);
        service.add(user);
    }

    @When("I change password for created user")
    public void changePasswordFirstTime() {
        User user = Entities.getUsers().getLatest();
        String newPassword = generatePassword();

        service.changePasswordForNewUser(user, newPassword);
    }

    @When("I set wrong user password")
    public void setWrongUserPassword() {
        User user = Entities.getUsers().getLatest();

        String passwordToReplace = generatePassword();
        log.info(String.format("Setting wrong password %s for user %s", passwordToReplace, user.getName()));
        user.setPassword(passwordToReplace);
        Entities.getUsers().addOrUpdateEntity(user);
    }

    @When("I change user password to $newPassword")
    public void changeUserPassword(final String newPassword) {
        User user = Entities.getUsers().getLatest();

        String passwordToReplace = newPassword;
        if ("random".equals(newPassword.toLowerCase())) {
            passwordToReplace = generatePassword();
        }
        if ("username".equals(newPassword.toLowerCase())) {
            passwordToReplace = user.getName();
        }

        log.info(String.format("Changing password from %s to %s", user.getPassword(), passwordToReplace));
        user.setNewPassword(passwordToReplace);
        context.put("user", user);
        service.update(user);
    }

}
