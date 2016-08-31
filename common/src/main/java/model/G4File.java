package model;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URI;

public class G4File extends File {

    private MediaType mediaType;

    public G4File(String pathname) {
        super(pathname);
    }

    public G4File(String parent, String child) {
        super(parent, child);
    }

    public G4File(File parent, String child) {
        super(parent, child);
    }

    public G4File(URI uri) {
        super(uri);
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

}
