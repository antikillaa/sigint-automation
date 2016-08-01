package http.requests.phonebook;


import http.requests.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class PhonebookRequest extends HttpRequest {

    private final static String URI = "/api/profile/phonebook";
    private MultiPart multiPart;
    Logger log = Logger.getLogger(PhonebookRequest.class);

    public PhonebookRequest() {
        super(URI);
        this.multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    public PhonebookRequest entries() {
        setURI(URI + "/entries");
        return this;
    }

    public PhonebookRequest upload() {
        setURI(URI + "/upload");
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
