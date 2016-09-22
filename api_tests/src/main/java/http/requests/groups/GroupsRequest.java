package http.requests.groups;

import http.requests.HttpRequest;
import http.requests.HttpMethod;
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
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(group);
        return this;
    }

    public GroupsRequest add(Group group) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(group);
        return this;
    }
}
