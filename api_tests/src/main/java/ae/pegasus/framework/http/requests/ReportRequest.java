package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.Report;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/workflows/3/perform-action/1/";

    public ReportRequest() {
        super(URI);
    }

    public ReportRequest add(Report report) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(report);
        return this;
    }
}
