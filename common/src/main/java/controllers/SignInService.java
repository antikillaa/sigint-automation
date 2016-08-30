package controllers;

import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.requests.SignInRequest;
import json.JsonCoverter;
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
        try {
            Response response = rsClient.post(
                    G4Properties.getRunProperties().getApplicationURL() + signInRequest.getURI(),
                    JsonCoverter.toJsonString(signInRequest));
            return response;
        } catch (NullReturnException e) {
            log.trace(e.getMessage(), e);
            throw new AssertionError("Sign in attempt failed!");
        }
    }
}
