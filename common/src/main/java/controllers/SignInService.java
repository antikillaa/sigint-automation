package controllers;

import http.requests.SignInRequest;
import json.RsClient;
import model.AppContext;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class SignInService {

    private static RsClient rsClient = new RsClient();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(SignInService.class);

    public Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        return rsClient.post(context.environment().getSigintHost() + signInRequest.getURI(), signInRequest);
    }
}
