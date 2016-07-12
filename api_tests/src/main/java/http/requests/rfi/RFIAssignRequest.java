package http.requests.rfi;

import http.requests.HttpRequest;


public class RFIAssignRequest extends HttpRequest {

    private static final String URI = "/api/rfi/";

    public RFIAssignRequest(String id) {
        super(URI + id + "/assign");
    }
}
