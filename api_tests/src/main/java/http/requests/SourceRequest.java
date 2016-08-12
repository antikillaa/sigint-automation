package http.requests;

public class SourceRequest extends HttpRequest {

    private final static String URI = "/api/sigint/admin/source";

    public SourceRequest() {
        super(URI);
    }

    public SourceRequest add() {
        setURI(URI + "/add");
        return this;
    }

    public SourceRequest get(String id) {
        setURI(URI + "/" + id + "/details");
        return this;
    }

    public SourceRequest delete(String id) {
        setURI(URI + "/" + id);
        return this;
    }

}
