package services;

import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.UploadFilesRequest;
import http.requests.UploadRequest;
import json.JsonCoverter;
import json.RsClient;
import model.*;
import model.Process;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

public class UploadFilesService {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(UploadFilesService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int upload(File file, FileMeta fileMeta) {
        log.info("Upload file" + file.getAbsolutePath() + "with 'meta' string..");
        UploadFilesRequest request = new UploadFilesRequest();

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

        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.post(sigintHost + request.getURI(), request.getBody(), request.getCookie());

        FileMeta entityFromResponse = JsonCoverter.readEntityFromResponse(response, FileMeta.class);
        context.put("fileMeta", entityFromResponse);

        return response.getStatus();
    }

    public FileMeta meta(String id) {
        log.info("Get Meta of uploaded file id:" + id);
        UploadFilesRequest request = new UploadFilesRequest().meta(id);

        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        return JsonCoverter.readEntityFromResponse(response, FileMeta.class);
    }

    public UploadDetails details(String id) {
        log.info("Get UploadDetails of uploaded file id:" + id);
        UploadRequest request = new UploadRequest().details(id);

        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        context.put("code", response.getStatus());

        return JsonCoverter.readEntityFromResponse(response, UploadDetails.class);
    }

    public List<Process> search(SearchFilter filter) {
        log.info("Get list of processed files..");
        UploadRequest request = new UploadRequest().search();

        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        UploadSearchResult searchResults = JsonCoverter.readEntityFromResponse(response, UploadSearchResult.class);
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Upload search");
        } else {
            return searchResults.getContent();
        }
    }

}
