package http.requests.phonebook;


import http.requests.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class DuSubscriberRequest extends HttpRequest {

    private final static String URI = "/api/sigint/du-subscribers";
    private MultiPart multiPart;
    Logger log = Logger.getLogger(DuSubscriberRequest.class);

    public DuSubscriberRequest() {
        super(URI);
        this.multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    public DuSubscriberRequest search() {
        this.setURI(URI + "/search");
        return this;
    }

    public DuSubscriberRequest upload() {
        this.setURI(URI + "/upload");
        return this;
    }

    public DuSubscriberRequest get(String id) {
        this.setURI(URI + "/entries/" + id);
        return this;
    }

    public void addBodyFile(String name, File file, MediaType type) {
        log.debug("Adding file to multipart body...");
        FileDataBodyPart filePart = new FileDataBodyPart(name, file, type);
        multiPart.bodyPart(filePart);
    }

    public MediaType getMediaType() {
        return multiPart.getMediaType();
    }

    public MultiPart getBody(){
        return multiPart;
    }
}
