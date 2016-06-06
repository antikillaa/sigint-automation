package http.requests.phonebook;

import http.requests.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class DuSubscribersRequest extends HttpRequest {

    private final static String URI = "/api/sigint/du-subscribers";
    private MultiPart multiPart;
    Logger log = Logger.getRootLogger();

    public DuSubscribersRequest() {
        super(URI);
        this.multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    public DuSubscribersRequest search() {
        this.setURI(URI + "/search");
        return this;
    }

    public DuSubscribersRequest upload() {
        this.setURI(URI + "/upload");
        return this;
    }

    public DuSubscribersRequest get(String id) {
        this.setURI(URI + "/" + id);
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
