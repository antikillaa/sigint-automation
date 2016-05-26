package http.requests.phonebook;

import http.requests.HttpRequest;

public class PhonebookSearchRequest extends HttpRequest {

    private final static String URI = "/api/sigint/unified-phonebook/search";

    public PhonebookSearchRequest() {
        super(URI);
    }

}
