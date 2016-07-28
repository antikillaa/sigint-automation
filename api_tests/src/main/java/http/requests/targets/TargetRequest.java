package http.requests.targets;

import http.requests.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class TargetRequest extends HttpRequest {

    private final static String URI = "/api/profile/targets";
    private MultiPart multiPart = new MultiPart();
    private Logger log = Logger.getLogger(TargetRequest.class);

    public TargetRequest() {
        super(URI);
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    public TargetRequest add() {
        return this;
    }

    public TargetRequest get(String id) {
        this.setURI(URI + "/" + id + "/details");
        return this;
    }

    public TargetRequest delete(String id) {
        this.setURI(URI + "/" + id );
        return this;
    }

    public TargetRequest upload() {
        this.setURI(URI + "/upload");
        return this;
    }

    public TargetRequest search() {
        this.setURI(URI + "/search");
        return this;
    }

    public TargetRequest findTargetGroups(String id) {
        this.setURI(URI + "/" + id + "/groups");
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
