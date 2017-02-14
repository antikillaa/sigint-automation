package http.requests;

import http.HttpMethod;
import model.TargetGroup;

public class TargetGroupRequest extends HttpRequest {

    private static final String URI = "/api/profiler/targetGroups";

    public TargetGroupRequest() {
        super(URI);
    }

    public TargetGroupRequest add(TargetGroup targetGroup) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
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

    public TargetGroupRequest listG4Compatibility() {
        this
                .setURI("/api/profiler/target-groups")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
    
}
