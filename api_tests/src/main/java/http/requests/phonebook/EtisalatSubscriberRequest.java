package http.requests.phonebook;

import abs.SearchFilter;
import http.requests.HttpRequest;
import http.requests.HttpRequestType;
import model.G4File;

import javax.ws.rs.core.MediaType;

public class EtisalatSubscriberRequest extends HttpRequest {

    private final static String URI = "/api/profile/etisalat-subscriber-data";

    public EtisalatSubscriberRequest() {
        super(URI);
    }

    public EtisalatSubscriberRequest search(SearchFilter filter) {
        this
                .setURI(URI + "/search")
                .setType(HttpRequestType.POST)
                .setPayload(filter);
        return this;
    }

    public EtisalatSubscriberRequest upload(G4File file) {
        addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        this
                .setURI(URI + "/upload")
                .setType(HttpRequestType.POST);
        return this;
    }

    public EtisalatSubscriberRequest get(String id) {
        this.setURI(URI + "/entries/" + id);
        return this;
    }

}
