package services;

import abs.SearchFilter;
import app_context.RunContext;
import http.G4HttpClient;
import http.G4Response;
import http.requests.UploadFilesRequest;
import http.requests.UploadRequest;
import json.JsonCoverter;
import model.*;
import model.Process;
import org.apache.log4j.Logger;

import java.util.List;

public class UploadFilesService {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(UploadFilesService.class);
    private RunContext context = RunContext.get();


    /**
     * UPLOAD new G4File
     *
     * @param file G4file (File with MediaType field)
     * @return HTTP status code
     */
    public int upload(G4File file) {
        log.info("Upload file" + file.getAbsolutePath() + " with 'meta' string..");

        UploadFilesRequest request = new UploadFilesRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

        FileMeta entityFromResponse = JsonCoverter.readEntityFromResponse(response, FileMeta.class);
        context.put("fileMeta", entityFromResponse);

        return response.getStatus();
    }

    /**
     * GET meta of uploaded file
     * API: GET /api/upload/files/{id}/meta
     *
     * @param id id of uploaded file
     * @return meta of uploaded file
     */
    public FileMeta meta(String id) {
        log.info("Get Meta of uploaded file id:" + id);
        UploadFilesRequest request = new UploadFilesRequest().meta(id);

        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, FileMeta.class);
    }

    /**
     * GET upload details of uploaded file
     * API: GET /api/sigint/upload/{id}/details
     *
     * @param id file id
     * @return upload details model
     */
    public UploadDetails details(String id) {
        log.info("Get UploadDetails of uploaded file id:" + id);
        UploadRequest request = new UploadRequest().details(id);

        G4Response response = g4HttpClient.sendRequest(request);
        context.put("code", response.getStatus());

        return JsonCoverter.readEntityFromResponse(response, UploadDetails.class);
    }

    /**
     * Search list of process details uploaded files
     *
     * @param filter search filter
     * @return List of process details uploaded files
     */
    public List<Process> search(SearchFilter filter) {
        log.info("Get Ingestion History list..");
        UploadRequest request = new UploadRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        UploadSearchResult searchResults = JsonCoverter.readEntityFromResponse(response, UploadSearchResult.class);
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Upload search");
        } else {
            return searchResults.getContent();
        }
    }

}
