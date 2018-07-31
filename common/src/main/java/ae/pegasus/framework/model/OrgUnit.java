
package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orgUnitId",
        "orgUnitName"
})
public class OrgUnit {

    @JsonProperty("orgUnitId")
    private String orgUnitId;
    @JsonProperty("orgUnitName")
    private String orgUnitName;

    @JsonProperty("orgUnitId")
    public String getOrgUnitId() {
        return orgUnitId;
    }

    @JsonProperty("orgUnitId")
    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    @JsonProperty("orgUnitName")
    public String getOrgUnitName() {
        return orgUnitName;
    }

    @JsonProperty("orgUnitName")
    public void setOrgUnitName(String orgUnitName) {
        this.orgUnitName = orgUnitName;
    }

}
