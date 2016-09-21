package http.requests;

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
                .setType(HttpRequestType.GET);
        return this;
    }

    public UserRequest add(User user) {
        this
                .setType(HttpRequestType.POST)
                .setPayload(user);
        return this;
    }
}
