package controllers;

import json.JsonCoverter;
import model.AppContext;
import model.Token;
import model.User;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class APILogin {

    private SignInService signService = new SignInService();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();


    public void signInWithCrendentials(String validness) {
        log.info("Signing in...");
        Response response;
        User user = context.get("user", User.class);
        String password;
        if (validness.toLowerCase().equals("incorrect")) {
            password = "incorrect";
        } else {
            password = user.getPassword();
        }
        response = signService.signIn(user.getName(), password);
        context.put("code", response.getStatus());
        if (response.getStatus() == 200) {
            Token token = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Token.class);
            context.environment().setToken(token);
        } else {
            context.put("message", response.readEntity(String.class));
        }
    }

}
