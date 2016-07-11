package controllers;

import errors.NullReturnException;
import http.requests.SignInRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import org.apache.log4j.Logger;
import services.UserService;

import javax.ws.rs.core.Response;

public class SignInService {

    private static RsClient rsClient = new RsClient();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(SignInService.class);

    public Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        try {
            Response response = rsClient.post(
                    context.environment().getSigintHost() + signInRequest.getURI(),
                    JsonCoverter.toJsonString(signInRequest));

            UserService userService = new UserService();
            userService.me();

            return response;
        } catch (NullReturnException e) {
            e.printStackTrace();
            throw new AssertionError("Sign in attempt failed!");
        }
    }
}
