package model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VoiceFile extends AbstractEntity {

    private ProfileJsonType jsonType = ProfileJsonType.VoiceFile;
    private String voiceEventId;

    public ProfileJsonType getJsonType() {
        return jsonType;
    }

    public void setJsonType(ProfileJsonType jsonType) {
        this.jsonType = jsonType;
    }

    public String getVoiceEventId() {
        return voiceEventId;
    }

    public void setVoiceEventId(String voiceEventId) {
        this.voiceEventId = voiceEventId;
    }
}
