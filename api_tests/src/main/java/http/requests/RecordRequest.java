package http.requests;

public class RecordRequest extends HttpRequest {

    private final static String URI = "/api/sigint/record/manual";

    public RecordRequest() {
        super(URI);
    }
}
