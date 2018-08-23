package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ReportOwner extends G4Entity {

    private User user;
    private String role;

    public User getUser() {
        return user;
    }

    public ReportOwner setUser(User user) {
        this.user = user;
        return this;
    }

    public String getRole() {
        return role;
    }

    public ReportOwner setRole(String role) {
        this.role = role;
        return this;
    }
    
}
