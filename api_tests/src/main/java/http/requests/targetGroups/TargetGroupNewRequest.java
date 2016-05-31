package http.requests.targetGroups;

import http.requests.HttpRequest;

public class TargetGroupNewRequest extends HttpRequest {

    private static final String URI = "/api/sigint/target-groups";

    public TargetGroupNewRequest() {
        super(URI);
    }
}
