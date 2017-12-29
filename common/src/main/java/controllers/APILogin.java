package controllers;

import app_context.AppContext;
import http.G4HttpClient;
import http.OperationResult;
import model.LoggedUser;
import model.RequestResult;
import model.Token;
import model.User;
import model.entities.Entities;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Given;
import org.junit.Assert;
import services.ResponsibilityService;
import services.TitleService;
import services.UserService;
import utils.StringUtils;

import java.util.List;

public class APILogin {

    private static AuthService signService = new AuthService();
    private static UserService userService = new UserService();
    private static AppContext context = AppContext.get();
    private static Logger log = Logger.getLogger(APILogin.class);
    private static final String ADMIN_ROLE = "admin";
    private static boolean cleaningIsRequired;

    public static User getUserByRole(String role) {
        User searchedUser = null;
        for (User user : Entities.getUsers()) {
            if (user.getRoles() != null && user.getRoles().contains(role.toUpperCase())) {
                searchedUser = user;
                break;
            }
        }
        return searchedUser;
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

    @Given("I sign in as user with permissions $permissions")
    public void signInWithPermissions(String permString) {
        String[] permissions = StringUtils.trimSpaces(permString.split(","));
        signInAsUser(getUserByRole(ADMIN_ROLE));

        User user;
        List<User> users = userService.getUsersWithPermissions(permissions);
        if (users.size() == 0) {
            user = userService.createUserWithPermissions(permissions);
            cleaningIsRequired = true;
            userService.addUser(user, permissions);
        } else {
            user = users.stream().findAny().orElse(null);
        }
        Assert.assertNotNull(user);
        signInAsUser(user);
    }

    @AfterStories
    public void afterStoryTearDown() {
        if (cleaningIsRequired) {
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

            cleaningIsRequired = false;
        }
    }
}
