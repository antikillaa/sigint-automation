package services;

import abs.SearchFilter;
import app_context.AppContext;
import app_context.RunContext;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.UploadFilesRequest;
import http.requests.UploadRequest;
import json.JsonCoverter;
import model.*;
import model.Process;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UploadFilesService {

    private static RsClient rsClient = new RsClient();
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(UploadFilesService.class);
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();
    private RunContext context = RunContext.get();


    public int upload(G4File file) {
        log.info("Upload file" + file.getAbsolutePath() + " with 'meta' string..");
        UploadFilesRequest request = new UploadFilesRequest();

        FileMeta fileMeta = initFileMeta(file);
        String meta;
        try {
            meta = JsonCoverter.toJsonString(fileMeta);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            log.error(e.getStackTrace());
            throw new Error("Error! Meta for uploading file is empty!");
        }

        request.addBodyString("meta", meta);
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        G4Response response = g4Client.post(sigintHost + request.getURI(), request.getBody(), request.getCookie());

        FileMeta entityFromResponse = JsonCoverter.readEntityFromResponse(response, FileMeta.class);
        context.put("fileMeta", entityFromResponse);

        return response.getStatus();
    }

    private FileMeta initFileMeta(G4File file) {
        Source source = context.get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();

        Meta meta = new Meta();
        meta.setFileName(file.getName());
        meta.setUserId(user.getId());
        meta.setSourceId(source.getId());

        FileMeta fileMeta = new FileMeta();
        fileMeta.setMeta(meta);
        String path = "/" + source.getType() + "/" + source.getName()
                + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date())  + file.getName();
        fileMeta.setName(path);
        fileMeta.setType(file.getMediaType().toString());

        return fileMeta;
    }

    public FileMeta meta(String id) {
        log.info("Get Meta of uploaded file id:" + id);
        UploadFilesRequest request = new UploadFilesRequest().meta(id);

        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());

        return JsonCoverter.readEntityFromResponse(response, FileMeta.class);
    }

    public UploadDetails details(String id) {
        log.info("Get UploadDetails of uploaded file id:" + id);
        UploadRequest request = new UploadRequest().details(id);

        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());
        context.put("code", response.getStatus());

        return JsonCoverter.readEntityFromResponse(response, UploadDetails.class);
    }

    public List<Process> search(SearchFilter filter) {
        log.info("Get Ingestion History list..");
        UploadRequest request = new UploadRequest().search();

        G4Response response = g4Client.post(sigintHost + request.getURI(), filter, request.getCookie());

        UploadSearchResult searchResults = JsonCoverter.readEntityFromResponse(response, UploadSearchResult.class);
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Upload search");
        } else {
            return searchResults.getContent();
        }
    }

}
