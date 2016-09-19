package model;

import abs.TeelaEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ReportOwner extends TeelaEntity {

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
