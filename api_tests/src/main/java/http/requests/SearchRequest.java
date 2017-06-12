package http.requests;

import http.HttpMethod;
import model.CBSearchFilter;

public class SearchRequest extends HttpRequest {

    private final static String URI = "/api/search/search/";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public SearchRequest() {
        super(URI);
    }

    /**
     * CB Search
     * API: POST /api/search/search
     * payload: {"keywords":"67576","pageSize":20,"page":0}
     */
    public SearchRequest search(CBSearchFilter filter) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(filter);
        return this;
    }
}
