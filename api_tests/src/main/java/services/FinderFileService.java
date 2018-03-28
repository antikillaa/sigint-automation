package services;

import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.FinderFileRequest;
import model.*;
import model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static json.JsonConverter.*;

public class FinderFileService implements EntityService<FinderFile> {

    private static final Logger log = Logger.getLogger(FinderFileService.class);
    private static final FinderFileRequest request = new FinderFileRequest();

    @Override
    public OperationResult<FinderFile> add(FinderFile entity) {
        log.info("Creating finder file");
        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<FinderFile> operationResult = new OperationResult<>(response, FinderFile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getFinderFiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(FinderFile entity) {
        log.info("Deleting finder file id:" + entity.getId() + ", name:" + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<String> deleteResult = new OperationResult<>(response);
        if (deleteResult.isSuccess()) {
            Entities.getFinderFiles().removeEntity(entity);
        }
        return deleteResult;
    }

    @Override
    public OperationResult<List<FinderFile>> search(SearchFilter filter) {
        throw new NotImplementedException("");
    }

    public OperationResult<List<FinderFile>> getTopLevelFiles(SearchFilter filter) {
        log.info("Get top of list of files, page:" + filter.getPage() + ", pageSize:" + filter.getPageSize());
        G4Response response = g4HttpClient.sendRequest(request.list(filter));

        List<FinderFile> fileList = jsonToObjectsList(response.getMessage(), FinderFile[].class, "data");
        return new OperationResult<>(response, fileList);
    }

    @Override
    public OperationResult<List<FinderFile>> list() {
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<FinderFile> update(FinderFile entity) {
        log.info("Updating finder file");
        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<FinderFile> operationResult = new OperationResult<>(response, FinderFile.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getFinderFiles().addOrUpdateEntity(operationResult.getEntity());
        } else {
            log.error("Update finder file process was failed");
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult<FinderFile> view(String id) {
        log.info("View finder file id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        OperationResult<FinderFile> operationResult = new OperationResult<>(response, FinderFile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getFinderFiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<ProfileAndTargetGroup[]> getContent(String fileID) {
        log.info("Get content of finder file id: " + fileID);

        G4Response response = g4HttpClient.sendRequest(request.getContent(fileID));

        return new OperationResult<>(response, ProfileAndTargetGroup[].class, "data");
    }

    public OperationResult<List<ProfileAndTargetGroup>> searchFileMembers(SearchFilter filter) {
        log.info("Get list of targets and finder files");
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<ProfileAndTargetGroup[]> operationResult =
                new OperationResult<>(response, ProfileAndTargetGroup[].class, "data");

        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public List<Profile> extractProfilesFromResponse(OperationResult<List<ProfileAndTargetGroup>> result) {
        List<Object> list = jsonToObjectsList(result.getMessage(), Object[].class, "data");
        return list.stream()
                .filter(o ->  ((LinkedHashMap) o).get("type").equals("Profile"))
                .map(o -> jsonToObject(toJsonString(o), Profile.class))
                .collect(Collectors.toList());
    }
}
