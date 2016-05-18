package http.requests;

import model.AppContext;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.ws.rs.core.Cookie;

@JsonIgnoreProperties(value={"URI", "context", "cookie"})
public class HttpRequest {

    private String URI;
    private AppContext context = AppContext.getContext();
    private Cookie cookie;

    public HttpRequest(String URI) {
        this.URI = URI;

    }

    public  Cookie getCookie() {
        if (cookie==null){
            cookie = new Cookie("t", context.environment().getToken().getValue());
        }
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
