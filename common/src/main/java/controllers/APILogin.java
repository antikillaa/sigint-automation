package controllers;

import app_context.RunContext;
import http.G4Response;
import json.JsonCoverter;
import app_context.AppContext;
import model.LoggedUser;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import services.UserService;

public class APILogin {

    private SignInService signService = new SignInService();
    private AppContext context = AppContext.get();
    private RunContext runContext = RunContext.get();
    Logger log = Logger.getRootLogger();


    /**
     * Sign in as user and add LoggedUser in AppContext.
     * @param user User for sign in.
     */
    public void signInAsUser(User user) {
        log.info("Signing in as user:"+ user);

        G4Response response = signService.signIn(user.getName(), user.getPassword());
        runContext.put("code", response.getStatus());
        if (response.getStatus() == 200) {
            Token token = JsonCoverter.readEntityFromResponse(response, Token.class);

            //update user
            User me = new UserService()
                    .me(token);
            me.setPassword(user.getPassword());
            me.setRoles(user.getRoles());

            context.setLoggedUser(new LoggedUser(me, token));
        } else {
            runContext.put("message", response.getMessage());
        }
    }

}
