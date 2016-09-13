package controllers;

import app_context.properties.G4Properties;
import http.G4Response;
import http.requests.SignInRequest;
import json.RsClient;
import http.client.G4Client;
import model.AppContext;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

class SignInService {
public class SignInService {

    private RsClient rsClient = new RsClient();
    private Logger log = Logger.getLogger(SignInService.class);
    private final String host = G4Properties.getRunProperties().getApplicationURL();
    private static G4Client g4Client = new G4Client();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(SignInService.class);

    Response signIn(String name, String password){
    public G4Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        return rsClient.post(host + signInRequest.getURI(), signInRequest);

        String url = context.environment().getSigintHost() + signInRequest.getURI();
        return g4Client.post(url, signInRequest);
    }
}
