package controllers;

import http.G4Response;
import json.JsonCoverter;
import model.AppContext;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import services.UserService;

public class APILogin {

    private SignInService signService = new SignInService();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();


    public void signInWithCrendentials(String validness) {
        log.info("Signing in...");

        User user = context.get("user", User.class);
        String password;
        if (validness.toLowerCase().equals("incorrect")) {
            password = "incorrect";
        } else {
            password = user.getPassword();
        }

        G4Response response = signService.signIn(user.getName(), password);
        context.put("code", response.getStatus());
        if (response.getStatus() == 200) {
            Token token = JsonCoverter.readEntityFromResponse(response, Token.class);
            context.environment().setToken(token);
        } else {
            context.put("message", response.getMessage());
        }

        UserService userService = new UserService();
        userService.me();
    }

}
