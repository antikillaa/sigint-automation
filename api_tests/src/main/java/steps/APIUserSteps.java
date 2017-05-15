package steps;

import model.entities.Entities;
import error_reporter.ErrorReporter;
import http.OperationResult;
import java.util.Date;
import java.util.List;
import json.JsonConverter;
import model.Team;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStory;
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
    private static final int LOGIN_DELAY = 5000;

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
    }

    @AfterStory
    public void deleteDefaultTeam() {
        if (UserService.getDefaultTeamId() != null) {
            log.info("Trying to remove default team...");
            Team team = teamService.view(UserService.getDefaultTeamId()).getEntity();
            teamService.remove(team);
            UserService.setDefaultTeamId(null);
        }
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() {
        User createdUser = Entities.getUsers().getLatest();
        User requestUser = context.get("user", User.class);

        log.debug("requested: " + JsonConverter.toJsonString(requestUser));
        log.debug("created: " + JsonConverter.toJsonString(createdUser));

        Assert.assertEquals(createdUser.getName().toLowerCase(), requestUser.getName().toLowerCase());
        Assert.assertEquals(createdUser.getFullName(), requestUser.getFullName());
        Assert.assertEquals(createdUser.getLanguages(), requestUser.getLanguages());
        Assert.assertEquals(createdUser.getStaffId(), requestUser.getStaffId());
    }

    static User getRandomUser() {
        return objectInitializer.randomEntity(User.class);
    }

    @When("I send delete user request")
    public void deleteUser() {
        User createdUser = Entities.getUsers().getLatest();
        if (createdUser.getCreatedBy() == null) {
            ErrorReporter.raiseError("You are trying to delete system user " + createdUser.getName());
        } else {
            service.remove(createdUser);
        }
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
            log.info("Create default team");
            Team team = objectInitializer.randomEntity(Team.class);
            String teamId = teamService.add(team).getEntity().getId();
            UserService.setDefaultTeamId(teamId);
        }
        user.setParentTeamId(UserService.getDefaultTeamId());

        context.put("user", user);
        service.add(user);
    }

    @When("I change password for the first time")
    public void changePasswordFirstTime() {
        User user = Entities.getUsers().getLatest();
        String newPassword = generatePassword();

        service.firstPasswordChange(user, newPassword);
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

    @Then("User last logon datetime is correct")
    public void checkLogonDatetime() {
        User user = Entities.getUsers().getLatest();
        Date userLogonDatetime = user.getLastLoginDate();

        if (userLogonDatetime == null) {
            ErrorReporter.reportAndRaiseError("Logon datetime is not initialized");
        }

        long deltaInMillis = System.currentTimeMillis() - userLogonDatetime.getTime();
        if (deltaInMillis > LOGIN_DELAY) {
            ErrorReporter.reportAndRaiseError("Incorrect logon datetime: " + userLogonDatetime);
        }
    }
}
