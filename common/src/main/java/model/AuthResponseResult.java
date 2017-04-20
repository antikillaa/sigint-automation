package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseResult {

  private String code;
  private String message;
  private AuthError[] errors;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public AuthError[] getErrors() {
    return errors;
  }

  public void setErrors(AuthError[] errors) {
    this.errors = errors;
  }

  private class AuthError {
    private String attribute;
    private String code;
    private String message;

    public String getAttribute() {
      return attribute;
    }

    public void setAttribute(String attribute) {
      this.attribute = attribute;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
