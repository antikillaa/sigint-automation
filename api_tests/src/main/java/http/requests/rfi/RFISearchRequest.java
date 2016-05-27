package http.requests.rfi;

import http.HttpRequest;



public class RFISearchRequest extends HttpRequest {

    private static final String URI = "/api/rfi/search";

    public RFISearchRequest() {
        super(URI);
    }


}
