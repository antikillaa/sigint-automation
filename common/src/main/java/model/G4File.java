package model;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URI;

/**
 * G4File extend java.io.File with MediaType field (used in ingestion)
 */
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

    public void setMediaTypeByFileExtension() {
        String filename = this.getName();

        if ((filename.endsWith(".wav"))) {
            this.setMediaType(PegasusMediaType.AUDIO);
        } else if ((filename.endsWith(".xls"))) {
            this.setMediaType(PegasusMediaType.MS_EXCEL_TYPE);
        } else  {
            this.setMediaType(PegasusMediaType.TEXT_CSV_TYPE);
        }
    }
}
