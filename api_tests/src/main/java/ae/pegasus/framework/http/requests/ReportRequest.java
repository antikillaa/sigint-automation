package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.http.HttpMethod;
import ae.pegasus.framework.model.Report;
import ae.pegasus.framework.model.ReportPayload;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/workflows/3/";

    public ReportRequest() {
        super(URI);
    }

    public ReportRequest generateNumber() {
        this
                .setURI(URI + "generate-number/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(null);
        return this;
    }

    public ReportRequest add(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/1/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest remove(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/3/")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest view(String id) {
        this
                .setURI(URI + "model/" + id + "?userAction=true")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ReportRequest submit(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/2")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest possibleOwners(Report report) {
        this
                .setURI(URI + "possible-owners/2")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(report);
        return this;
    }

    public ReportRequest takeOwnership(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/5")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest approveReport(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/6")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest returnAuthor(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/8")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest rejectReport(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/9")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest cancelReportOwned(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/7")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

    public ReportRequest cancelReportNotOwned(Report report) {
        ReportPayload reportPayload = new ReportPayload();
        reportPayload.setData(report);
        this
                .setURI(URI + "perform-action/4")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportPayload);
        return this;
    }

}
