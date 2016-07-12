package http.requests;

public class ReportRequest extends HttpRequest {

    private final static String URI = "/api/reports/";

    public ReportRequest() {
        super(URI);
    }
}
