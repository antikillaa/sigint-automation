package controllers;

import http.requests.SignInRequest;
import json.RsClient;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

class SignInService {

    private RsClient rsClient = new RsClient();
    private Logger log = Logger.getLogger(SignInService.class);

    Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        return rsClient.post(context.environment().getSigintHost() + signInRequest.getURI(), signInRequest);
    }
}
