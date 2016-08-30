package controllers;

import app_context.RunContext;
import json.JsonCoverter;
import app_context.AppContext;
import model.LoggedUser;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import services.UserService;

import javax.ws.rs.core.Response;

public class APILogin {

    private SignInService signService = new SignInService();
    private AppContext context = AppContext.get();
    private RunContext runContext = RunContext.get();
    Logger log = Logger.getRootLogger();


    public void signInAsUser(User user) {
        log.info("Signing in as user:"+ user);
        Response response;
        
        response = signService.signIn(user.getName(), user.getPassword());
        runContext.put("code", response.getStatus());
        if (response.getStatus() == 200) {
            Token token = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Token.class);
            context.setLoggedUser(new LoggedUser(user, token));
        } else {
            runContext.put("message", response.readEntity(String.class));
        }

        UserService userService = new UserService();
        userService.me();
    }

}
