package http.requests.targetGroups;

import http.requests.HttpRequest;
import http.requests.HttpRequestType;
import model.TargetGroup;

public class TargetGroupRequest extends HttpRequest {

    private static final String URI = "/api/profile/target-groups";

    public TargetGroupRequest() {
        super(URI);
    }

    public TargetGroupRequest add(TargetGroup targetGroup) {
        this
                .setURI(URI)
                .setType(HttpRequestType.PUT)
                .setPayload(targetGroup);
        return this;
    }

    public TargetGroupRequest get(String id) {
        this.setURI(URI + "/" + id + "/details");
        return this;
    }

    public TargetGroupRequest delete(String id) {
        this
                .setURI(URI + "/" + id )
                .setType(HttpRequestType.DELETE);
        return this;
    }

    public TargetGroupRequest update(TargetGroup targetGroup) {
        this
                .setType(HttpRequestType.POST)
                .setPayload(targetGroup);
        return this;
    }
}
