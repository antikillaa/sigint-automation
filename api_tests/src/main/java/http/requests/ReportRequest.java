package http.requests;

import http.HttpRequest;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/";

    public ReportRequest() {
        super(URI);
    }
}
