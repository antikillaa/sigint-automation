package http.requests;

import abs.SearchFilter;
import http.HttpMethod;
import model.Record;

public class RecordRequest extends HttpRequest {

    private final static String URI = "/api/sigint/record";

    public RecordRequest() {
        super(URI);
    }

    public RecordRequest manual(Record record) {
        this
                .setURI(URI + "/manual")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(record);
        return this;
    }

    public RecordRequest search(SearchFilter filter) {
        this
                .setURI(URI + "/search?withTargets=true")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(filter);
        return this;
    }
}
