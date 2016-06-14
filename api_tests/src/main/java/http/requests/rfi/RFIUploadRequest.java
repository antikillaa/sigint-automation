package http.requests.rfi;


import http.HttpRequest;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Created by dm on 4/15/16.
 */
public class RFIUploadRequest extends HttpRequest {

    private static final String URI = "/api/rfi/upload";
    private MultiPart multiPart;
    Logger log = Logger.getRootLogger();

    public RFIUploadRequest() {
        super(URI);
        this.multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
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

