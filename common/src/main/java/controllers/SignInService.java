package controllers;

import app_context.properties.G4Properties;
import http.requests.SignInRequest;
import json.RsClient;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

class SignInService {

    private RsClient rsClient = new RsClient();
    private Logger log = Logger.getLogger(SignInService.class);
    private final String host = G4Properties.getRunProperties().getApplicationURL();

    Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        return rsClient.post(host + signInRequest.getURI(), signInRequest);
    }
}
