package http.requests;

import http.HttpMethod;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.MediaType;
import json.JsonConverter;
import model.FileMeta;
import model.G4File;
import model.Meta;
import model.MetaProperties;
import model.Source;

public class UploadFilesRequest extends HttpRequest {

    private final static String URI = "/api/upload/files/";
    private static String parentRemotePath;

    public UploadFilesRequest() {
        super(URI);
    }

    /**
     * GET meta of uploaded file
     * API: GET /api/upload/files/{id}/meta
     *
     * @param id id of uploaded file
     * @return GET meta of uploaded file request
     */
    public UploadFilesRequest meta(String id) {
        setURI(URI + id + "/meta")
                .setHttpMethod(HttpMethod.GET) ;

        return this;
    }
    
    /**
     * Send POST request to notify sigint that file is uploaded
     * @param fileMeta {@link FileMeta} of the file to be notified
     * @return formed notify request
     */
    public UploadFilesRequest notify(FileMeta fileMeta) {
        setURI("/api/sigint/upload/notify")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(fileMeta);
        
        return this;
    }

    /**
     * Multipart UPLOAD file request
     * API: POST /api/upload/files
     *
     * @param file file for upload;
     * @param source of file;
     * @param ownerId of file;
     * @return UploadFilesRequest
     */
    public UploadFilesRequest upload(G4File file, Source source, String ownerId) {
        FileMeta fileMeta = initFileMeta(file, source, ownerId);
        String meta = JsonConverter.toJsonString(fileMeta);
        addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        addBodyString("meta", meta);

        file.deleteOnExit();
        this.setURI(URI).setHttpMethod(HttpMethod.POST);

        return this;
    }

    /**
     * Generate meta entity for uploaded file
     *
     * @param file file for upload
     * @return FileMeta model for uploaded file
     */
    private FileMeta initFileMeta(G4File file, Source source, String ownerId) {

        String name;
        String basename = file.getName().substring(0, file.getName().lastIndexOf("."));
        String path = "/" + source.getType().toLetterCode()
            + "/" + source.getName()
            + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date())
            + basename + "/";


        /*  Meta file contains relative paths of audio files.
            Place audio files into parentRemotePath subfolder to bind them together */
        if (file.getName().contains(".xls") || file.getName().contains(".csv")) {
            parentRemotePath = path;
            name = parentRemotePath + file.getName();
        } else {
            name = parentRemotePath + file.getParentFile().getName() + "/" + file.getName();
        }

        FileMeta fileMeta = new FileMeta();
        fileMeta.setName(name);
        fileMeta.setOwner(ownerId);
        fileMeta.setSourceId(source.getId());
        Meta meta = new Meta();
        MetaProperties properties = new MetaProperties();
        properties.setRecordType(source.getRecordType());
        properties.setSourceType(source.getType());
        meta.setProperties(properties);
        fileMeta.setMeta(meta);
        fileMeta.setType(file.getMediaType().toString());

        return fileMeta;
    }
}
