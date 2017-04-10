package http.requests;

import http.HttpMethod;
import model.Profile;

public class ProfileDraftRequest extends HttpRequest {

    private static final String URI = "/api/profiler/profileDrafts";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public ProfileDraftRequest() {
        super(URI);
    }

    public ProfileDraftRequest create(Profile profile) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(profile);
        return this;
    }

    public ProfileDraftRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public ProfileDraftRequest view(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ProfileDraftRequest publish(Profile profile) {
        this
                .setURI(URI + "/" + profile.getId() + "/publish")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(profile);
        return this;
    }

    public ProfileDraftRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ProfileDraftRequest update(Profile profile) {
        this
                .setURI(URI + "/" + profile.getId())
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(profile);
        return this;
    }
}
