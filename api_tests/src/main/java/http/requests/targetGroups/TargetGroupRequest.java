package http.requests.targetGroups;

import http.HttpRequest;

public class TargetGroupRequest extends HttpRequest {

    private static final String URI = "/api/sigint/target-groups";

    public TargetGroupRequest() {
        super(URI);
    }

    public TargetGroupRequest get(String id) {
        this.setURI(URI + "/" + id + "/details");
        return this;
    }
}
