package http.requests;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import services.ContextService;

import javax.ws.rs.core.Cookie;

@JsonIgnoreProperties(value={"URI", "context", "cookie"})
public class HttpRequest {

    private String URI;
    private Cookie cookie;

    public HttpRequest(String URI) {
        this.URI = URI;
    }

    //todo:Move getCookie to higher level. Remove from model
    public  Cookie getCookie() {
        String tokenValue = ContextService.getToken();
        if (tokenValue == null) {
                return null;
            }
        cookie = new Cookie("t", tokenValue);
        return cookie;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
