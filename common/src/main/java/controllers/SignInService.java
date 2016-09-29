package controllers;

import http.G4HttpClient;
import http.G4Response;
import http.requests.SignInRequest;

class SignInService {

    private G4HttpClient g4HttpClient = new G4HttpClient();

    G4Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest(name, password);
        return g4HttpClient.sendRequest(signInRequest);
    }
}