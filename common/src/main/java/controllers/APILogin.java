package controllers;

import app_context.AppContext;
import errors.NullReturnException;
import http.G4HttpClient;
import http.OperationResult;
import model.LoggedUser;
import model.RequestResult;
import model.Token;
import model.User;
import model.entities.Entities;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Given;
import org.junit.Assert;
import services.ResponsibilityService;
import services.TitleService;
import services.UserService;
import users_management.StorageUsersManager;
import utils.StringUtils;

import java.util.Arrays;
import java.util.List;

public class APILogin {

    private static AuthService signService = new AuthService();
    private static AppContext context = AppContext.get();
    private static Logger log = Logger.getLogger(APILogin.class);
    private static final String ADMIN_ROLE = "admin";
    private static boolean сleanupIsNeeded;

    public static User getUserByRole(String role) {
        try {
            return Entities.getUsers().getEntity(role);
        } catch (NullReturnException e) {
            log.error("Cannot find user by given role:" + role);
            return null;
        }
    }

    /**
     * Sign in as user and add LoggedUser in AppContext.
     *
     * @param role predefined Role of User for sign in.
     */
    public OperationResult<Token> signInAsUser(String role) {
        User user = getUserByRole(role);
        return signInAsUser(user);
    }

    /**
     * Sign in as user and add LoggedUser in AppContext.
     *
     * @param user User for sign in.
     */
    public OperationResult<Token> signInAsUser(User user) {
        log.info("Signing in as user: " + user);

        OperationResult<Token> operationResult = signService.signIn(user.getName(), user.getPassword());
        if (operationResult.isSuccess()) {
            Token token = operationResult.getEntity();
            context.setLoggedUser(new LoggedUser(user));
            G4HttpClient.setCookie(Token.tokenCookieProperty, token.getValue());

            //update user
            OperationResult<User> meResult = new UserService().me();
            User me = meResult.getEntity();
            me.setPassword(user.getPassword());

            context.setLoggedUser(new LoggedUser(me));
        }
        return operationResult;
    }

    /**
     * Sign out as user
     *
     * @return {@link OperationResult<RequestResult>}
     */
    public OperationResult<RequestResult> signOut() {
        log.info("Signing out");

        OperationResult<RequestResult> operationResult = signService.signOut();
        if (operationResult.isSuccess()) {
            context.setLoggedUser(null);
        }

        return operationResult;
    }

    private User getUserWithPermissions(String... permissions) {
        StorageUsersManager manager = StorageUsersManager.getManager();
        log.debug("Finding users with permissions: " + Arrays.toString(permissions));

        List<User> users = manager.getUsersWithPermissions(permissions);
        if (users.size() == 0) {
            log.debug("Users are not found. Creating new user with required permissions");
            signInAsUser(getUserByRole(ADMIN_ROLE));
            User user = UserService.createUserWithPermissions(permissions);
            сleanupIsNeeded = true;
            manager.addUser(user, permissions);
            log.debug("User is created");
            return user;
        }
        return users.stream().findAny().orElse(null);
    }

    @Given("I sign in as user with permissions $permissions")
    public void signInWithPermissions(String permString) {
        String[] permissions = StringUtils.trimSpaces(permString.split(","));
        User user = getUserWithPermissions(permissions);
        Assert.assertNotNull(user);
        signInAsUser(user);
    }

    @AfterStory
    public void afterStoryTearDown() {
        if (сleanupIsNeeded) {
            log.info("TearDown for temp users, titles & responsibilities");

            Assert.assertTrue("Unable login as admin user!", signInAsUser(ADMIN_ROLE).isSuccess());

            List<OperationResult> operationResults;

            operationResults = new UserService().removeAll();
            for (OperationResult result : operationResults) {
                if (!result.isSuccess()) {
                    log.error("Unable to delete User. Code:" + result.getCode() + ", Response:" + result.getMessage());
                }
            }

            operationResults = new TitleService().removeAll();
            for (OperationResult result : operationResults) {
                if (!result.isSuccess()) {
                    log.error("Unable to delete Title. Code:" + result.getCode() + ", Response:" + result.getMessage());
                }
            }

            operationResults = new ResponsibilityService().removeAll();
            for (OperationResult result : operationResults) {
                if (!result.isSuccess()) {
                    log.error("Unable to delete Responsibility. Code:" + result.getCode() + ", Response:" + result.getMessage());
                }
            }

            сleanupIsNeeded = false;
        }
    }
}
