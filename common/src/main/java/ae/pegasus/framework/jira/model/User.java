package ae.pegasus.framework.jira.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by dm on 4/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;
}
