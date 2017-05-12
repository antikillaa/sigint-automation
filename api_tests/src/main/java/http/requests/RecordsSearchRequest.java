package http.requests;

import model.SearchFilter;
import http.HttpMethod;

public class RecordsSearchRequest extends HttpRequest {

    private final static String URI = "/api/profiler/search/records";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public RecordsSearchRequest() {
        super(URI);
    }

    /**
     * CB Search
     * API: POST /api/profiler/search/records
     * payload: {"keywords":"67576","pageSize":20,"page":0}
     */
    public RecordsSearchRequest search(SearchFilter filter) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(filter);
        return this;
    }
}
