package http.requests.phonebook;

import http.requests.HttpRequest;

public class UnifiedPhonebookSearchRequest extends HttpRequest {

    private final static String URI = "/api/profile/unified-phonebook/search";

    public UnifiedPhonebookSearchRequest() {
        super(URI);
    }

}
