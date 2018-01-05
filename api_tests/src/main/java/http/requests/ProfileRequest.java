package http.requests;

import http.HttpMethod;
import model.Profile;

import java.util.ArrayList;

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


    /**
     * POST /profiles/merge merge
     *
     * @param firstProfileId  first Profile ID
     * @param secondProfileId second Profile ID
     * @return {@link ProfileRequest}
     */
    public ProfileRequest merge(String firstProfileId, String secondProfileId) {

        ArrayList<String> mergeIDs = new ArrayList<>();
        mergeIDs.add(firstProfileId);
        mergeIDs.add(secondProfileId);

        this
                .setURI(URI + "/merge")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(mergeIDs);
        return this;
    }

    public ProfileRequest identifiersSummary(String profileID) {
        this
                .setURI(URI + "/" + profileID + "/identifierAggregations")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ProfileRequest summary(String profileID) {
        this
                .setURI(URI + "/" + profileID + "/summary")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public ProfileRequest getOrCreateDraft(String profileID) {
        Profile profile = new Profile();
        profile.setId(profileID);
        this
                .setURI(URI + "/" + profileID + "/draft")
                .setHttpMethod(HttpMethod.POST);
        return this;
    }

}
