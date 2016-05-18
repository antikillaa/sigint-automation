package services;
import errors.NullReturnException;
import http.requests.SignInRequest;
import model.AppContext;
import model.EntityList;
import model.SearchFilter;
import model.User;
import rs.client.JsonCoverter;
import rs.client.RsClient;
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

    public Response signIn(User user){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setName(user.getName());
        signInRequest.setPassword(user.getPassword());
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
