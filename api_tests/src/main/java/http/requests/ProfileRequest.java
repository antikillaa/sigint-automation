package http.requests;

import http.HttpMethod;

public class ProfileRequest extends HttpRequest {

    private static final String URI = "/api/profiler/profiles";

    /**
     * Build HTTP request.
     * By Default: HttpMethod = GET, MediaType = APPLICATION_JSON.
     */
    public ProfileRequest() {
        super(URI);
    }

    /**
     * GET /profiles/{profileId} getProfile
     *
     * @param id ID of profile
     * @return {@link ProfileRequest}
     */
    public ProfileRequest view(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    /**
     * DELETE /profiles/{profileId} deleteProfile
     *
     * @param id ID of profile
     * @return {@link ProfileRequest}
     */
    public ProfileRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }
}
