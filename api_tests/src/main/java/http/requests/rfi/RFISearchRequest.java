package http.requests.rfi;

import abs.SearchFilter;
import http.requests.HttpRequest;
import http.requests.HttpRequestType;


public class RFISearchRequest extends HttpRequest {

    private static final String URI = "/api/rfi/search";

    public RFISearchRequest(SearchFilter filter) {
        super(URI);
        this
                .setType(HttpRequestType.POST)
                .setPayload(filter);
    }


}
