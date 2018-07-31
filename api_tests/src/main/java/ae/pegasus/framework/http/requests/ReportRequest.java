package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.Report;
import ae.pegasus.framework.model.ReportPayload;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/workflows/3/perform-action/1/";

    public ReportRequest() {
        super(URI);
    }

    public ReportRequest add(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }
}
