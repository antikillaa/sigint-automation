package http.requests;

import http.HttpMethod;

public class TitleRequest extends HttpRequest {

    public static final String URI = "/api/auth/titles";

    public TitleRequest() {
        super(URI);
    }

    public TitleRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
