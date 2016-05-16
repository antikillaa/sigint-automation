package jira.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by dm on 4/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String name;
    private String value;

    @Override
    public String toString() {
        return name+"="+value;
    }
}
