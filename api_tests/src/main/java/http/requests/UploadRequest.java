package http.requests;

public class UploadRequest extends HttpRequest {

    private final static String URI = "/api/sigint/upload";

    public UploadRequest() {
        super(URI);
    }

    public UploadRequest search() {
        setURI(URI + "/search");
        return this;
    }

    public UploadRequest details(String id) {
        setURI(URI + "/" + id + "/details");
        return this;
    }
}
