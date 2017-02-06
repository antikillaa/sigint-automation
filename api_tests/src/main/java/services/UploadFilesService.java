package services;

import abs.SearchFilter;
import http.*;
import http.requests.UploadFilesRequest;
import http.requests.UploadRequest;
import model.*;
import model.Process;
import org.apache.log4j.Logger;

import java.util.List;

public class UploadFilesService {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(UploadFilesService.class);
    
    /**
     * Multipart UPLOAD file
     * API: POST /api/upload/files
     *
     * @param file G4file (File with MediaType field)
     * @return {@link OperationResult}
     */
    public OperationResult<FileMeta> upload(G4File file, Source source, String ownerId) {
        log.info("Upload file" + file.getAbsolutePath() + " with 'meta' string..");
        UploadFilesRequest uploadRequest = new UploadFilesRequest().upload(file, source, ownerId);
        G4Response uploadResponse = g4HttpClient.sendRequest(uploadRequest);
        OperationResult<FileMeta> uploadResult = new OperationResult<>(uploadResponse, FileMeta.class);
        
        if (!uploadResult.isSuccess()) {
            OperationsResults.throwError(uploadResult);
        }
        G4Response notifyResponse = sendNotify(uploadResult.getResult());
        return new OperationResult<>(notifyResponse, uploadResult.getResult());
        
    }
    
    
    private G4Response sendNotify(FileMeta fileMeta) {
        UploadFilesRequest notifyRequest = new UploadFilesRequest().notify(fileMeta);
        G4Response notifyResponse = g4HttpClient.sendRequest(notifyRequest);
        return notifyResponse;
    }
    

    /**
     * GET meta of uploaded file
     * API: GET /api/upload/files/{id}/meta
     *
     * @param id id of uploaded file
     * @return meta of uploaded file
     */
    public OperationResult<FileMeta> meta(String id) {
        log.info("Get Meta of uploaded file id:" + id);
        UploadFilesRequest request = new UploadFilesRequest().meta(id);
        G4Response response = g4HttpClient.sendRequest(request);
        return new OperationResult<>(response, FileMeta.class);
        
    }

    /**
     * GET upload details of uploaded file
     * API: GET /api/sigint/upload/{id}/details
     *
     * @param id file id
     * @return upload details model
     */
    public OperationResult<UploadDetails> details(String id) {
        log.info("Get UploadDetails of uploaded file id:" + id);
        UploadRequest request = new UploadRequest().details(id);
        G4Response response = g4HttpClient.sendRequest(request);
        UploadDetails details = JsonConverter.readEntityFromResponse(response, UploadDetails.class);
        return new OperationResult<>(response, details);
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

        UploadSearchResult searchResults = JsonConverter.readEntityFromResponse(response, UploadSearchResult.class);
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Upload search");
        } else {
            return searchResults.getContent();
        }
    }

}
