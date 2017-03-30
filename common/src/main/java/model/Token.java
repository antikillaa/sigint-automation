package model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Token {

    private String value;

    @JsonProperty("accessToken")
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}