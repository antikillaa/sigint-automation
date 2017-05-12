package http.requests;

import model.SearchFilter;
import http.HttpMethod;
import model.Tag;

public class TagRequest extends HttpRequest {

    private static final String URI = "/api/workflow/record-tags";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public TagRequest() {
        super(URI);
    }

    public TagRequest create(Tag entity) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(entity);
        return this;
    }

    public TagRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public TagRequest search(SearchFilter filter) {
        this
                .setURI(URI + "/search")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(filter);
        return this;
    }

    public TagRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }
}
