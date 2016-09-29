package http.requests;

import app_context.AppContext;
import http.HttpMethod;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Http request model.
 */
@JsonIgnoreProperties(value = {"URI", "context", "cookie", "httpMethod", "mediaType", "payload"})
public class HttpRequest {

    /**
     * URL path
     */
    private String URI;
    /**
     * HTTP request httpMethod, such as: GET/PUT/POST/DELETE
     */
    private HttpMethod httpMethod;
    private String mediaType;
    private Object payload;
    private MultiPart multiPart;
    private Cookie cookie;

    private Logger log = Logger.getLogger(HttpRequest.class);

    /**
     * Build HTTP request.
     * By Default GET MediaType.APPLICATION_JSON Request.
     *
     * @param URI path string
     */
    public HttpRequest(String URI) {
        this.URI = URI;
        httpMethod = HttpMethod.GET;
        mediaType = MediaType.APPLICATION_JSON;
        multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    //todo:Move getCookie to higher level. Remove from model
    public Cookie getCookie() {
        if (cookie == null) {
            try {
                String tokenValue = AppContext.get().getLoggedUser().getToken().getValue();
                this.cookie = new Cookie("t", tokenValue);
            } catch (NullPointerException e) {
                return null;
            }
        }
        return cookie;
    }

    public HttpRequest setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public String getURI() {
        return URI;
    }

    public HttpRequest setURI(String URI) {
        this.URI = URI;
        return this;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpRequest setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public HttpRequest setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public HttpRequest setPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    /**
     * Add file to Miltipart payload
     *
     * @param name name of FileDataBodyPart
     * @param file file entity
     * @param type MediaType of file
     * @return HttpRequest
     */
    protected HttpRequest addBodyFile(String name, File file, MediaType type) {
        log.debug("Adding file to multipart body...");
        FileDataBodyPart filePart = new FileDataBodyPart(name, file, type);

        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        multiPart.bodyPart(filePart);

        setPayload(multiPart);

        file.deleteOnExit();

        return this;
    }

    /**
     * Add string field to Miltipart payload
     *
     * @param fieldName field name
     * @param value     string value
     * @return HttpRequest
     */
    protected HttpRequest addBodyString(String fieldName, String value) {
        FormDataBodyPart part = new FormDataBodyPart(fieldName, value);
        multiPart.bodyPart(part);

        setPayload(multiPart);

        return this;
    }

}
