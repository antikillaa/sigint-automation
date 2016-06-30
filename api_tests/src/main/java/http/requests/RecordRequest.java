package http.requests;

import http.HttpRequest;

public class RecordRequest extends HttpRequest {

    private final static String URI = "/api/sigint/record/manual";

    public RecordRequest() {
        super(URI);
    }
}
