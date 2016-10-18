package http.requests;

public class ReportCategoriesRequest extends HttpRequest {

    private static final String URI = "/api/reports/categories";

    /**
     * Build HTTP request.
     * By Default GET MediaType.APPLICATION_JSON Request.
     */
    public ReportCategoriesRequest() {
        super(URI);
    }
}
