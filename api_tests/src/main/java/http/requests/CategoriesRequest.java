package http.requests;

import http.HttpRequest;

public class CategoriesRequest extends HttpRequest {

    private static final String URI = "/api/reports/categories";

    public CategoriesRequest() {
        super(URI);
    }
}
