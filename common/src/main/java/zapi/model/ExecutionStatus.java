package zapi.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ExecutionStatus {

    private String status;
    private boolean changeAssignee = false;

    public String getStatus() {
        return status;
    }

    public ExecutionStatus setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean isChangeAssignee() {
        return changeAssignee;
    }

    public ExecutionStatus setChangeAssignee(boolean changeAssignee) {
        this.changeAssignee = changeAssignee;
        return this;
    }
}
