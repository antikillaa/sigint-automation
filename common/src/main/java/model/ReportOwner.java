package model;

import abs.TeelaEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ReportOwner extends TeelaEntity {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class User extends TeelaEntity {

        private String name;
        private String staffId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        @Override
        public <T extends TeelaEntity> T generate() {
            return null;
        }
    }

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

    @Override
    public <T extends TeelaEntity> T generate() {
        return null;
    }
}
