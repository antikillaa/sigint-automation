package http.requests.phonebook;

import http.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class EtisalatSubscriberDataRequest extends HttpRequest {

    private final static String URI = "/api/sigint/etisalat-subscriber-data";
    private MultiPart multiPart;
    Logger log = Logger.getLogger(DuSubscriberRequest.class);

    public EtisalatSubscriberDataRequest() {
        super(URI);
        this.multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    public EtisalatSubscriberDataRequest search() {
        this.setURI(URI + "/search");
        return this;
    }

    public EtisalatSubscriberDataRequest upload() {
        this.setURI(URI + "/upload");
        return this;
    }

    public EtisalatSubscriberDataRequest get(String id) {
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
