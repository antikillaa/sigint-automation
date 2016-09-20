package http.requests.rfi;

import http.requests.HttpRequest;
import http.requests.HttpRequestType;

/**
 * Created by dm on 5/16/16.
 */
public class RFIDeleteRequest extends HttpRequest {

    private static final String URI = "/api/rfi/";

    public RFIDeleteRequest(String rfiID) {
        super(URI + rfiID);
        this.setType(HttpRequestType.DELETE);
    }
}
