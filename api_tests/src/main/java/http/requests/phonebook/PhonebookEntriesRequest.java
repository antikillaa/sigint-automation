package http.requests.phonebook;


import http.HttpRequest;

public class PhonebookEntriesRequest extends HttpRequest {

    private final static String URI = "/api/sigint/phonebook/entries";

    public PhonebookEntriesRequest() {
        super(URI);
    }
}
