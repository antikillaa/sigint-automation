package model;

import abs.AbstractEntity;
import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProfileAndTargetGroup extends AbstractEntity {

    @DataIgnore
    private Object assignedTeams;
    @DataIgnore
    private Object assignedUsers;
    @DataIgnore
    private Object assignmentPriority;
    @DataIgnore
    private ProfileJsonType jsonType;
    private String name;

    public Object getAssignedTeams() {
        return assignedTeams;
    }

    public void setAssignedTeams(Object assignedTeams) {
        this.assignedTeams = assignedTeams;
    }

    public Object getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Object assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Object getAssignmentPriority() {
        return assignmentPriority;
    }

    public void setAssignmentPriority(Object assignmentPriority) {
        this.assignmentPriority = assignmentPriority;
    }

    public ProfileJsonType getJsonType() {
        return jsonType;
    }

    public void setJsonType(ProfileJsonType jsonType) {
        this.jsonType = jsonType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
