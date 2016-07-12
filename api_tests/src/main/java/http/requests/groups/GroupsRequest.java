package http.requests.groups;

import http.requests.HttpRequest;

public class GroupsRequest extends HttpRequest {

    private final static String URI = "/api/auth/groups";

    public GroupsRequest() {
        super(URI);
    }

    public GroupsRequest update(String id) {
        this.setURI(URI + "/" + id);
        return this;
    }
}
