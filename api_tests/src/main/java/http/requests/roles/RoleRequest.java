package http.requests.roles;

import http.requests.HttpRequest;
import http.requests.HttpRequestType;
import model.PegasusMediaType;
import model.Role;

public class RoleRequest extends HttpRequest {

    private static final String URI = "/api/auth/roles";

    public RoleRequest() {
        super(URI);
    }

    public RoleRequest add(Role role) {
        this
                .setType(HttpRequestType.POST)
                .setMediaType(PegasusMediaType.PEGASUS_JSON)
                .setPayload(role);
        return this;
    }
}
