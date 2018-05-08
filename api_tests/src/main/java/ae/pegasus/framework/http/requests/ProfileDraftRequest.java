package ae.pegasus.framework.http.requests;

import ae.pegasus.framework.model.Profile;
import ae.pegasus.framework.model.Voice;

import javax.ws.rs.core.MediaType;
import java.io.File;

import static ae.pegasus.framework.http.HttpMethod.*;

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
                .setHttpMethod(POST)
                .setPayload(profile);
        return this;
    }

    public ProfileDraftRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(DELETE);
        return this;
    }

    public ProfileDraftRequest view(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(GET);
        return this;
    }

    public ProfileDraftRequest publish(Profile profile) {
        this
                .setURI(URI + "/" + profile.getId() + "/publish")
                .setHttpMethod(POST)
                .setPayload(profile);
        return this;
    }

    public ProfileDraftRequest list() {
        this
                .setURI(URI)
                .setHttpMethod(GET);
        return this;
    }

    public ProfileDraftRequest update(Profile profile) {
        profile.getProperties().setProfileVersion(
                profile.getProperties().getProfileVersion() == null ? 1 : profile.getProperties().getProfileVersion() + 1);
        this
                .setURI(URI + "/" + profile.getId())
                .setHttpMethod(PUT)
                .setPayload(profile);
        return this;
    }

    /**
     * POST /api/profiler/profileDrafts/{id}/image/upload
     *
     * @param profileID profile ID
     * @param file      image file
     * @return {@link ProfileDraftRequest}
     */
    public ProfileDraftRequest imageUpload(String profileID, File file) {
        addBodyFile("file", file, new MediaType("image", "jpeg"));
        this
                .setURI(URI + "/" + profileID + "/image/upload")
                .setHttpMethod(POST);
        return this;
    }

    public ProfileDraftRequest deleteImage(String profileID) {
        this
                .setURI(URI + "/" + profileID + "/image/delete")
                .setHttpMethod(DELETE);
        return this;
    }

    /**
     * GET /api/profiler/profileDrafts/{id}/voice/events
     */
    public ProfileDraftRequest voiceEvents(String profileID) {
        this
                .setURI(URI + "/" + profileID + "/voice/events")
                .setHttpMethod(GET);
        return this;
    }

    /**
     * GET /api/profiler/profileDrafts/{id}/voice
     */
    public ProfileDraftRequest voice(String profileID) {
        this
                .setURI(URI + "/" + profileID + "/voice")
                .setHttpMethod(GET);
        return this;
    }

    /**
     * POST /api/profiler/profileDrafts/{id}/voice
     */
    public ProfileDraftRequest createVoice(String profileID, Voice voice) {
        this
                .setURI(URI + "/" + profileID + "/voice")
                .setHttpMethod(POST)
                .setPayload(voice);
        return this;
    }

    public ProfileDraftRequest uploadAudioFile(String profileID, File file) {
        addBodyFile("file", file, new MediaType("audio", "x-m4a"));
        this
                .setURI(URI + "/" + profileID + "/voice/upload")
                .setHttpMethod(POST);
        return this;
    }
}
