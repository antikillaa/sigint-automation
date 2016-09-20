package http.requests;

import model.Report;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/";

    public ReportRequest() {
        super(URI);
    }

    public ReportRequest add(Report report) {
        this
                .setType(HttpRequestType.PUT)
                .setPayload(report);
        return this;
    }
}
