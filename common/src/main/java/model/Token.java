package model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Token {

    String value;

    @JsonProperty("t")
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}