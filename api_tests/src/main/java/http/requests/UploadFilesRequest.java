package http.requests;

import app_context.AppContext;
import app_context.RunContext;
import errors.NullReturnException;
import http.HttpMethod;
import json.JsonCoverter;
import model.*;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFilesRequest extends HttpRequest {

    private final static String URI = "/api/upload/files";
    private Logger log = Logger.getLogger(UploadFilesRequest.class);

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
        setURI(URI + "/" + id + "/meta");
        return this;
    }

    /**
     * Multipart UPLOAD file request
     * API: POST /api/upload/files
     *
     * @param file file for upload
     * @return UploadFilesRequest
     */
    public UploadFilesRequest upload(G4File file) {
        FileMeta fileMeta = initFileMeta(file);
        String meta;
        try {
            meta = JsonCoverter.toJsonString(fileMeta);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace());
            throw new Error("Error! Meta for uploading file is empty!");
        }

        addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        addBodyString("meta", meta);
        file.deleteOnExit();

        this.setHttpMethod(HttpMethod.POST);
        return this;
    }

    /**
     * Generate meta entity for uploaded file
     *
     * @param file file for upload
     * @return FileMeta model for uploaded file
     */
    private FileMeta initFileMeta(G4File file) {
        Source source = RunContext.get().get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();

        Meta meta = new Meta();
        meta.setFileName(file.getName());
        meta.setUserId(user.getId());
        meta.setSourceId(source.getId());

        FileMeta fileMeta = new FileMeta();
        fileMeta.setMeta(meta);
        String path = "/" + source.getType() + "/" + source.getName()
                + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + file.getName();
        fileMeta.setName(path);
        fileMeta.setType(file.getMediaType().toString());

        return fileMeta;
    }
}
