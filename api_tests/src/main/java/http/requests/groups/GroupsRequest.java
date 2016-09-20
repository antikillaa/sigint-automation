package http.requests.groups;

import http.requests.HttpRequest;
import http.requests.HttpRequestType;
import model.Group;
import model.PegasusMediaType;

public class GroupsRequest extends HttpRequest {

    private final static String URI = "/api/auth/groups";

    public GroupsRequest() {
        super(URI);
        this.setMediaType(PegasusMediaType.PEGASUS_JSON);
    }

    public GroupsRequest update(Group group) {
        this
                .setURI(URI + "/" + group.getId())
                .setType(HttpRequestType.PUT)
                .setPayload(group);
        return this;
    }

    public GroupsRequest add(Group group) {
        this
                .setURI(URI)
                .setType(HttpRequestType.POST)
                .setPayload(group);
        return this;
    }
}
