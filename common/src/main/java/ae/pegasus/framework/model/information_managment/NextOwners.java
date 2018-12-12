package ae.pegasus.framework.model.information_managment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "orgUnitId",
        "orgUnitName",
        "type"
})

public class NextOwners {

    @JsonProperty("ownerId")
    private String ownerId;
    @JsonProperty("ownerName")
    private String ownerName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("ownerFullName")
    private String ownerFullName;

    @JsonProperty("ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    @JsonProperty("ownerId")
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty("ownerName")
    public String getOwnerName() {
        return ownerName;
    }

    @JsonProperty("ownerName")
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("ownerFullName")
    public String getOwnerFullName() {
        return ownerFullName;
    }

    @JsonProperty("ownerFullName")
    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
}
