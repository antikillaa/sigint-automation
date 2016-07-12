package http.requests.roles;

import http.requests.HttpRequest;

public class RoleRequest extends HttpRequest {

    private static final String URI = "/api/auth/roles";

    public RoleRequest() {
        super(URI);
    }
}
