package services;
import errors.NullReturnException;
import http.requests.SignInRequest;
import model.AppContext;
import abs.EntityList;
import abs.SearchFilter;
import model.User;
import json.JsonCoverter;
import json.RsClient;
import service.EntityService;

import javax.ws.rs.core.Response;

/**
 * Created by dm on 3/11/16.
 */
public class UserService implements EntityService<User> {

    private static RsClient rsClient = new RsClient();
    private AppContext context = AppContext.getContext();

    public int addNew(User user) {
        return 0;
    }

    public int remove(User entity) {
        return 0;
    }

    public EntityList<User> list(SearchFilter filter) {
        return null;
    }

    public int update(User entity) {
        return 0;
    }

    public User view(String id) {
        return null;
    }

    public Response signIn(String name, String password){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(name);
        signInRequest.setPassword(password);
        try {
            Response response = rsClient.post(context.environment().getSigintHost()
                    + signInRequest.getURI(),
                    JsonCoverter.toJsonString(signInRequest));
            return response;
        } catch (NullReturnException e) {
            e.printStackTrace();
            throw new AssertionError("Sign in attempt failed!");
        }

    }
}
