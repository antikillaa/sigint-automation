package model;

import http.requests.HttpRequest;

public class SourcesRequest extends HttpRequest {

    public static final String URI = "/api/sigint/sources";

    public SourcesRequest() {
        super(URI);
    }
}
