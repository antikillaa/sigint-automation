package http.requests;

import http.HttpMethod;

public class DictionaryRequest extends HttpRequest {

    private static final String URI = "/api/workflow/dictionaries/";

    public DictionaryRequest() {
        super(URI);
    }

    public DictionaryRequest sources() {
        this
                .setURI(URI + "sources")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public DictionaryRequest subSources() {
        this
                .setURI(URI + "sub-sources")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
