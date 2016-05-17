package http.requests.rfi;

import http.requests.HttpRequest;

/**
 * Created by dm on 5/16/16.
 */
public class RFIDeleteRequest extends HttpRequest {

    static final String URI = "/api/rfi/";
    public RFIDeleteRequest(String rfiID) {
        super(URI+rfiID);
    }
}
