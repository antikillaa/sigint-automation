package http.requests;

import http.HttpMethod;
import model.RecordCategory;

public class RecordCategoriesRequest extends HttpRequest {

    private static final String URI = "/api/sigint/admin/record-categories/";

    /**
     * Build HTTP request.
     * By Default: HttpMethod - GET, MediaType - APPLICATION_JSON.
     */
    public RecordCategoriesRequest() {
        super(URI);
    }

    /**
     * Create a new one record-category request.
     * HttpMethod: PUT
     *
     * @param recordCategory new recordCategory entity for payload
     * @return RecordCategoriesRequest
     */
    public RecordCategoriesRequest add(RecordCategory recordCategory) {
        this
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(recordCategory);
        return this;
    }

    /**
     * Get list of record-categories request.
     * HttpMethod: GET
     *
     * @return RecordCategoriesRequest
     */
    public RecordCategoriesRequest list() {
        this.setHttpMethod(HttpMethod.GET);
        return this;
    }
}
