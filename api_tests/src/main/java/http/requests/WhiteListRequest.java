package http.requests;


import static org.junit.Assert.assertNotNull;

import http.HttpMethod;
import model.G4File;
import model.PegasusMediaType;
import model.SearchFilter;
import model.Whitelist;

public class WhiteListRequest extends HttpRequest {

    private final static String URI = "/api/workflow/whitelist/";

    public WhiteListRequest() {
        super(URI);
    }

    public WhiteListRequest list() {
        this
            .setURI(URI)
            .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public WhiteListRequest get(String id) {
        this
            .setURI(URI + id)
            .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public WhiteListRequest add(Whitelist whitelist) {
        this
            .setURI(URI)
            .setHttpMethod(HttpMethod.POST)
            .setPayload(whitelist);
        return this;
    }

    public WhiteListRequest update(Whitelist whitelist) {
        // update version field
        whitelist.setVersion(whitelist.getVersion() == null ? 1 : whitelist.getVersion() + 1);

        this
            .setURI(URI + whitelist.getId())
            .setHttpMethod(HttpMethod.PUT)
            .setPayload(whitelist);
        return this;
    }

    public WhiteListRequest delete(String WhitelistId) {
        this
            .setURI(URI + WhitelistId)
            .setHttpMethod(HttpMethod.DELETE);
        return this;
    }


    public WhiteListRequest search(SearchFilter searchFilter) {
        this
            .setURI(URI + "search")
            .setHttpMethod(HttpMethod.POST)
            .setPayload(searchFilter);
        return this;
    }

    public WhiteListRequest upload(G4File file) {
        assertNotNull("null object for whitelist upload", file);
        addBodyFile("file", file, PegasusMediaType.TEXT_CSV_TYPE);
        this
            .setURI(URI + "import")
            .setHttpMethod(HttpMethod.POST);
        return this;
    }

}
