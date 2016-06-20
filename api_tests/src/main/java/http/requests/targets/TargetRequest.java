package http.requests.targets;

import http.HttpRequest;

public class TargetRequest extends HttpRequest {

    private final static String URI = "/api/sigint/targets";

    public TargetRequest() {
        super(URI);
    }

    public TargetRequest add() {
        return this;
    }

    public TargetRequest get(String id) {
        this.setURI(URI + "/" + id + "/details");
        return this;
    }


}
