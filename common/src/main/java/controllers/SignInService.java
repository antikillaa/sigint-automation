package controllers;

import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.SignInRequest;
import model.RequestResult;
import model.Token;

class SignInService {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private static SignInRequest request = new SignInRequest();

    OperationResult<Token> signIn(String name, String password){
        G4Response response = g4HttpClient.sendRequest(request.signIn(name, password));
        return new OperationResult<>(response, Token.class);
    }

    OperationResult<RequestResult> singOut(){
        G4Response response = g4HttpClient.sendRequest(request.singOut());
        return new OperationResult<>(response, RequestResult.class);
    }
}