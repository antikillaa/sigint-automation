package controllers;

import app_context.AppContext;
import http.G4HttpClient;
import http.OperationResult;
import model.LoggedUser;
import model.RequestResult;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import services.UserService;

public class APILogin {

    private AuthService signService = new AuthService();
    private AppContext context = AppContext.get();
    private Logger log = Logger.getLogger(APILogin.class);

    /**
     * Sign in as user and add LoggedUser in AppContext.
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
            me.setRoles(user.getRoles());

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
        if(operationResult.isSuccess()) {
            context.setLoggedUser(null);
        }

        return operationResult;
    }


}
