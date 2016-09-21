package http.requests.rfi;

import http.requests.HttpRequest;
import http.requests.HttpRequestType;


public class RFIAssignRequest extends HttpRequest {

    private static final String URI = "/api/rfi/";

    public RFIAssignRequest(String id) {
        super(URI + id + "/assign");
        this.setType(HttpRequestType.POST);
    }
}
