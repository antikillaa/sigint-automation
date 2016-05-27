package http.requests.rfi;

import http.HttpRequest;

/**
 * Created by dm on 5/18/16.
 */
public class RFIAssignRequest extends HttpRequest {

    private static final String URI = "/api/rfi/";

    public RFIAssignRequest(String id) {
        super(URI + id + "/assign");
    }
}
