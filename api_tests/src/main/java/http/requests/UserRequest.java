package http.requests;

import http.HttpRequest;

public class UserRequest extends HttpRequest {

    private static final String URI = "/api/auth/users";

    public UserRequest() {
        super(URI);
    }

    public UserRequest me() {
        this.setURI(URI + "/me");
        return this;
    }
}
