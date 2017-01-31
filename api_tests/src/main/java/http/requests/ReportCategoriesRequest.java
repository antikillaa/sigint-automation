package http.requests;

import http.HttpMethod;
import model.ReportCategory;

public class ReportCategoriesRequest extends HttpRequest {

    private static final String URI = "/api/reports/categories";

    /**
     * Build HTTP request.
     * By Default GET MediaType.APPLICATION_JSON Request.
     */
    public ReportCategoriesRequest() {
        super(URI);
    }

    public ReportCategoriesRequest create(ReportCategory reportCategory) {
        this
                .setURI("/api/reports/admin/categories")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportCategory);
        return this;
    }
}
