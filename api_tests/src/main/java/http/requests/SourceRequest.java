package http.requests;

import model.Source;

public class SourceRequest extends HttpRequest {

    private final static String URI = "/api/sigint/admin/source";

    public SourceRequest() {
        super(URI);
    }

    public SourceRequest add(Source source) {
        this
                .setURI(URI + "/add")
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(source);
        return this;
    }

    public SourceRequest get(String id) {
        setURI(URI + "/" + id + "/details");
        return this;
    }

    public SourceRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public SourceRequest update(Source source) {
        this
                .setHttpMethod(HttpMethod.POST)
                .setPayload(source);
        return this;
    }

}
