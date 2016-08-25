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

import javax.ws.rs.client.Entity;
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
        UploadFilesRequest request = new UploadFilesRequest();

        String meta = null;
        try {
            meta = JsonCoverter.toJsonString(fileMeta);
            log.info(meta);
        } catch (NullReturnException e) {
            e.printStackTrace();
        }

        request.addBodyString("meta", meta);
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());

        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        FileMeta entityFromResponse = JsonCoverter.readEntityFromResponse(response, FileMeta.class);
        context.put("fileMeta", entityFromResponse);

        try {
            log.info("response: " + JsonCoverter.toJsonString(entityFromResponse));
        } catch (NullReturnException e) {
            e.printStackTrace();
        }
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
