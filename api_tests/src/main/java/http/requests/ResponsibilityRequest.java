package http.requests;

import http.HttpMethod;

public class ResponsibilityRequest extends HttpRequest {

    private static final String URI = "/api/auth/responsibilities";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public ResponsibilityRequest() {
        super(URI);
    }

    public ResponsibilityRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
