package http.requests;

import http.HttpMethod;
import model.ReportCategory;

public class ReportCategoriesRequest extends HttpRequest {

    private static final String URI = "/api/reports/admin/categories/";
    private static final String URI_DEFAULT = "/api/reports/categories";

    /**
     * Build HTTP request.
     * By Default GET MediaType.APPLICATION_JSON Request.
     */
    public ReportCategoriesRequest() {
        super(URI_DEFAULT);
    }

    public ReportCategoriesRequest create(ReportCategory reportCategory) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(reportCategory);
        return this;
    }

    public ReportCategoriesRequest delete(String id) {
        this
                .setURI(URI + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public ReportCategoriesRequest update(ReportCategory reportCategory) {
        this
                .setURI(URI + reportCategory.getId())
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(reportCategory);
        return this;
    }

    public ReportCategoriesRequest filter(Long updatedAfter) {
        this
                .setURI(URI_DEFAULT + "?updatedAfter=" + updatedAfter)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ReportCategoriesRequest view(String id) {
        this
            .setURI(URI + id)
            .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ReportCategoriesRequest list() {
        this
                .setURI(URI_DEFAULT)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
