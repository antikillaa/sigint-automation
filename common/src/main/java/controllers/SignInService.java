package controllers;

import app_context.properties.G4Properties;
import http.G4Response;
import http.client.G4Client;
import http.requests.SignInRequest;
import org.apache.log4j.Logger;

class SignInService {

    private G4Client g4Client = new G4Client();
    private Logger log = Logger.getLogger(SignInService.class);
    private final String host = G4Properties.getRunProperties().getApplicationURL();

    G4Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        return g4Client.post(host + signInRequest.getURI(), signInRequest);
    }
}