package controllers;

import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.SignInRequest;
import model.Token;

class SignInService {

    private G4HttpClient g4HttpClient = new G4HttpClient();

    OperationResult<Token> signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest(name, password);
        G4Response response =  g4HttpClient.sendRequest(signInRequest);
        return new OperationResult<>(response, Token.class);
    }
}