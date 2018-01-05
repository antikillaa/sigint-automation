package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.data_providers.profiler.AssignmentPriorityProvider;
import data_for_entity.data_providers.profiler.ProfileAndTargetGroupNameProvider;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProfileAndTargetGroup extends AbstractEntity {

    @DataIgnore
    private List<String> assignedTeams = new ArrayList<>(Arrays.asList("00")); // Default team
    @DataIgnore
    private Object assignedUsers;
    @DataProvider(AssignmentPriorityProvider.class)
    private Integer assignmentPriority; // 0, 1, 2 (Regular, High, Urgent)
    @DataIgnore
    private ProfileJsonType jsonType;
    @DataProvider(ProfileAndTargetGroupNameProvider.class)
    private String name;

    public Object getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Object assignedUsers) {
        this.assignedUsers = assignedUsers;
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

    public List<String> getAssignedTeams() {
        return assignedTeams;
    }

    public void setAssignedTeams(List<String> assignedTeams) {
        this.assignedTeams = assignedTeams;
    }

    public Integer getAssignmentPriority() {
        return assignmentPriority;
    }

    public void setAssignmentPriority(Integer assignmentPriority) {
        this.assignmentPriority = assignmentPriority;
    }
}
