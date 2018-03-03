package services;

import app_context.properties.G4Properties;
import errors.OperationResultError;
import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.UploadFoldersRequest;
import model.FileMetaFilter;
import model.FolderMeta;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static http.G4HttpClient.Protocol.HTTP;

public class UploadFoldersService {

  private static final Logger log = Logger.getLogger(UploadFoldersService.class);
  private static G4HttpClient g4HttpClient = new G4HttpClient(G4Properties.getRunProperties().getApplicationURL(), HTTP);
  private static UploadFoldersRequest request = new UploadFoldersRequest();

  public List<FolderMeta> searchFolders(FileMetaFilter filter) {
    log.info("Search Data Folders..");
    G4Response response = g4HttpClient.sendRequest(request.search(filter));

    OperationResult<FolderMeta[]> operationResult = new OperationResult<>(response, FolderMeta[].class);
    if (operationResult.isSuccess() && operationResult.getEntity() != null) {
      return Arrays.asList(operationResult.getEntity());
    } else {
      throw new OperationResultError(operationResult);
    }
  }

  public OperationResult<Integer> countFolders(FileMetaFilter filter) {
    log.info("Count Data Folders..");
    G4Response response = g4HttpClient.sendRequest(request.count(filter));

    return new OperationResult<>(response, Integer.class);
  }
}
