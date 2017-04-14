package http.requests;


import abs.SearchFilter;
import http.HttpMethod;
import model.Whitelist;

public class WhiteListRequest extends HttpRequest {

    private final static String URI = "/api/workflow/whitelist";

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
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public WhiteListRequest add(Whitelist whitelist){

          this
                  .setURI(URI)
                  .setHttpMethod(HttpMethod.POST)
                  .setPayload(whitelist);
          return this;
    }

    public WhiteListRequest update(Whitelist whitelist){

        this
                .setURI(URI+"/"+whitelist.getId())
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(whitelist);
        return this;
    }

    public WhiteListRequest delete(String WhitelistId){

        this
                .setURI(URI + "/" + WhitelistId)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }


    public WhiteListRequest search(SearchFilter searchFilter){

        this
                .setURI(URI + "/search")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(searchFilter);
        return this;
    }


}
