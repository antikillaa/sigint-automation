package controllers;

import app_context.properties.G4Properties;
import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.AuthRequest;
import model.RequestResult;
import model.Token;

import static http.G4HttpClient.Protocol.HTTP;

class AuthService {

    private static G4HttpClient g4HttpClient = new G4HttpClient(G4Properties.getRunProperties().getApplicationURL(), HTTP);
    private static AuthRequest request = new AuthRequest();

    OperationResult<Token> signIn(String name, String password) {
        G4Response response = g4HttpClient.sendRequest(request.signIn(name, password));
        return new OperationResult<>(response, Token.class);
    }

    OperationResult<RequestResult> signOut() {
        G4Response response = g4HttpClient.sendRequest(request.singOut());
        OperationResult<RequestResult> operationResult = new OperationResult<>(response,
            RequestResult.class);
        if (operationResult.isSuccess()) {
            G4HttpClient.removeCookie();
        }
        return operationResult;
    }
}