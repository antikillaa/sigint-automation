package ae.pegasus.framework.controllers;

import ae.pegasus.framework.app_context.properties.G4Properties;
import ae.pegasus.framework.http.G4HttpClient;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.AuthRequest;
import ae.pegasus.framework.model.RequestResult;
import ae.pegasus.framework.model.Token;

import static ae.pegasus.framework.http.G4HttpClient.Protocol.HTTP;

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