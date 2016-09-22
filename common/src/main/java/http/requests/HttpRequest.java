package http.requests;

import app_context.AppContext;
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
@JsonIgnoreProperties(value = {"URI", "context", "cookie", "type", "mediaType", "payload"})
public class HttpRequest {

    /**
     * Path
     */
    private String URI;
    /**
     * HTTP request type, such as: GET/PUT/POST/DELETE
     */
    private HttpRequestType type;
    private String mediaType;
    private Object payload;
    private MultiPart multiPart;
    private Logger log = Logger.getLogger(HttpRequest.class);

    /**
     * Build HTTP request.
     * By Default GET MediaType.APPLICATION_JSON Request.
     *
     * @param URI path string
     */
    public HttpRequest(String URI) {
        this.URI = URI;
        type = HttpRequestType.GET;
        mediaType = MediaType.APPLICATION_JSON;
        multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    //todo:Move getCookie to higher level. Remove from model
    public Cookie getCookie() {
        String tokenValue;
        try {
            tokenValue = AppContext.get().getLoggedUser().getToken().getValue();
        } catch (NullPointerException e) {
            return null;
        }
        return new Cookie("t", tokenValue);
    }

    public String getURI() {
        return URI;
    }

    public HttpRequest setURI(String URI) {
        this.URI = URI;
        return this;
    }

    public HttpRequestType getType() {
        return type;
    }

    public HttpRequest setType(HttpRequestType type) {
        this.type = type;
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

    protected HttpRequest addBodyFile(String name, File file, MediaType type) {
        log.debug("Adding file to multipart body...");
        FileDataBodyPart filePart = new FileDataBodyPart(name, file, type);

        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        multiPart.bodyPart(filePart);

        setPayload(multiPart);

        file.deleteOnExit();

        return this;
    }

    protected HttpRequest addBodyString(String fieldName, String value) {
        FormDataBodyPart part = new FormDataBodyPart(fieldName, value);
        multiPart.bodyPart(part);

        setPayload(multiPart);

        return this;
    }

}
