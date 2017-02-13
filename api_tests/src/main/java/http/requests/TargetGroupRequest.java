package http.requests;

import http.HttpMethod;
import model.TargetGroup;

public class TargetGroupRequest extends HttpRequest {

    private static final String URI = "/api/profiler/target-groups";

    public TargetGroupRequest() {
        super(URI);
    }

    public TargetGroupRequest add(TargetGroup targetGroup) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(targetGroup);
        return this;
    }

    public TargetGroupRequest get(String id) {
        this.setURI(URI + "/" + id + "/details");
        return this;
    }

    public TargetGroupRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public TargetGroupRequest update(TargetGroup targetGroup) {
        this
                .setHttpMethod(HttpMethod.POST)
                .setPayload(targetGroup);
        return this;
    }

    public TargetGroupRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
