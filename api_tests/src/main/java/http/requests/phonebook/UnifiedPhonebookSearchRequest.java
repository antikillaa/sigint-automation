package http.requests.phonebook;

import abs.SearchFilter;
import http.requests.HttpRequest;
import http.requests.HttpRequestType;

public class UnifiedPhonebookSearchRequest extends HttpRequest {

    private final static String URI = "/api/profile/unified-phonebook/search";

    public UnifiedPhonebookSearchRequest(SearchFilter filter) {
        super(URI);
        this
                .setType(HttpRequestType.POST)
                .setPayload(filter);
    }

}
