package ae.pegasus.framework.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VoiceFile extends AbstractEntity {

    private ProfileType jsonType = ProfileType.VoiceFile;
    private String voiceEventId;

    public ProfileType getJsonType() {
        return jsonType;
    }

    public void setJsonType(ProfileType jsonType) {
        this.jsonType = jsonType;
    }

    public String getVoiceEventId() {
        return voiceEventId;
    }

    public void setVoiceEventId(String voiceEventId) {
        this.voiceEventId = voiceEventId;
    }
}
