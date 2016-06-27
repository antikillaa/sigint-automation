package http.requests.groups;

import http.HttpRequest;

public class GroupsRequest extends HttpRequest {

    private final static String URI = "/api/auth/groups";

    public GroupsRequest() {
        super(URI);
    }
}
