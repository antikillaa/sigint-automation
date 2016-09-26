package http.requests;

import http.HttpMethod;
import model.Report;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/";

    public ReportRequest() {
        super(URI);
    }

    public ReportRequest add(Report report) {
        this
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(report);
        return this;
    }
}
