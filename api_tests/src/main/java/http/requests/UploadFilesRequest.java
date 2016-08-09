package http.requests;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class UploadFilesRequest extends HttpRequest {

    private final static String URI = "/api/upload/files";

    private MultiPart multiPart = new MultiPart();
    private Logger log = Logger.getLogger(UploadFilesRequest.class);

    public UploadFilesRequest() {
        super(URI);
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
