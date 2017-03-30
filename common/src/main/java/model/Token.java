package model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Token {

    public static final String tokenProperty = "accessToken";
    public static final String tokenCookieProperty = "t";
    private String value;

    @JsonProperty(tokenProperty)
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}