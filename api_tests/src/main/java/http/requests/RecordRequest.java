package http.requests;

public class RecordRequest extends HttpRequest {

    private final static String URI = "/api/sigint/record";

    public RecordRequest() {
        super(URI);
    }

    public RecordRequest manual() {
        setURI(URI + "/manual");
        return this;
    }

    public RecordRequest search() {
        setURI(URI + "/search?withTargets=true");
        return this;
    }
}
