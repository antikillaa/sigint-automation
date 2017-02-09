package controllers;

import app_context.AppContext;
import http.OperationResult;
import http.OperationsResults;
import model.LoggedUser;
import model.RequestResult;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import services.UserService;

public class APILogin {

    private SignInService signService = new SignInService();
    private AppContext context = AppContext.get();
    private Logger log = Logger.getLogger(APILogin.class);

    /**
     * Sign in as user and add LoggedUser in AppContext.
     * @param user User for sign in.
     */
    public OperationResult<Token> signInAsUser(User user) {
        log.info("Signing in as user: " + user);

        OperationResult<Token> operationResult = signService.signIn(user.getName(), user.getPassword());
        OperationsResults.setResult(operationResult);
        if (operationResult.isSuccess()) {
            Token token = operationResult.getResult();
            context.setLoggedUser(new LoggedUser(user, token));
            //update user
            OperationResult<User> meResult = new UserService().me();
            User me = meResult.getResult();
            me.setPassword(user.getPassword());
            me.setRoles(user.getRoles());

            context.setLoggedUser(new LoggedUser(me, token));
        }
        return operationResult;
    }

    /**
     * Sing out as user
     *
     * @return {@link OperationResult<RequestResult>}
     */
    public OperationResult<RequestResult> singOut() {
        log.info("Signing out");

        OperationResult<RequestResult> operationResult = signService.singOut();
        OperationsResults.setResult(operationResult);

        if(operationResult.isSuccess()) {
            context.setLoggedUser(null);
        }

        return operationResult;
    }


}
