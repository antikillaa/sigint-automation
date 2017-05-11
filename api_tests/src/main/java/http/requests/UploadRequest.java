package http.requests;

import abs.SearchFilter;
import http.HttpMethod;

public class UploadRequest extends HttpRequest {

    private final static String URI = "/api/sigint/upload/";

    public UploadRequest() {
        super(URI);
    }

    public UploadRequest search(SearchFilter filter) {
        this
                .setURI(URI + "/search")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(filter);

        return this;
    }

    public UploadRequest details(String id) {
        setURI(URI + id + "/details")
                .setHttpMethod(HttpMethod.GET);

        return this;
    }
}
