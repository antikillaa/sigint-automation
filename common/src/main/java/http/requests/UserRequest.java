package http.requests;

import http.HttpMethod;
import model.PegasusMediaType;
import model.User;

public class UserRequest extends HttpRequest {

    private static final String URI = "/api/auth/users";

    public UserRequest() {
        super(URI);
        this.setMediaType(PegasusMediaType.PEGASUS_JSON);
    }

    public UserRequest me() {
        this
                .setURI(URI + "/me")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public UserRequest add(User user) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(user);
        return this;
    }

    public UserRequest update(User user) {
        this
                .setURI(URI + "/" + user.getId())
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(user);
        return this;
    }

    public UserRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public UserRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

}
