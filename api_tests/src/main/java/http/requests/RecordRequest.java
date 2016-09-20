package http.requests;

import abs.SearchFilter;
import model.Record;

public class RecordRequest extends HttpRequest {

    private final static String URI = "/api/sigint/record";

    public RecordRequest() {
        super(URI);
    }

    public RecordRequest manual(Record record) {
        this
                .setURI(URI + "/manual")
                .setType(HttpRequestType.POST)
                .setPayload(record);
        return this;
    }

    public RecordRequest search(SearchFilter filter) {
        this
                .setURI(URI + "/search?withTargets=true")
                .setType(HttpRequestType.POST)
                .setPayload(filter);
        return this;
    }
}
