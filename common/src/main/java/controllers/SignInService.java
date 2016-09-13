package controllers;

import http.G4Response;
import http.requests.SignInRequest;
import http.client.G4Client;
import model.AppContext;
import org.apache.log4j.Logger;

public class SignInService {

    private static G4Client g4Client = new G4Client();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(SignInService.class);

    public G4Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);

        String url = context.environment().getSigintHost() + signInRequest.getURI();
        return g4Client.post(url, signInRequest);
    }
}
