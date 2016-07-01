package http.requests.roles;

import http.HttpRequest;

public class RoleRequest extends HttpRequest {

    private static final String URI = "/api/auth/roles";

    public RoleRequest() {
        super(URI);
    }
}
