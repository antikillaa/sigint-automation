package services;

import errors.OperationResultError;
import model.SearchFilter;
import model.entities.Entities;
import http.G4Response;
import http.OperationResult;
import http.requests.TitleRequest;
import model.AuthResponseResult;
import model.Title;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TitleService implements EntityService<Title> {

    private static Logger log = Logger.getLogger(TitleService.class);
    private static TitleRequest request = new TitleRequest();

    @Override
    public OperationResult<Title> add(Title entity) {
        log.info("Create title, name:" + entity.getDisplayName());
        G4Response response = g4HttpClient.sendRequest(request.create(entity));

        OperationResult<Title> operationResult = new OperationResult<>(response, Title.class);
        if (operationResult.isSuccess()) {
            Entities.getTitles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Title entity) {
        log.info("Delete title id:" + entity.getId() + " name:" + entity.getDisplayName());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<AuthResponseResult> operationResult = new OperationResult<>(response, AuthResponseResult.class);
        if (operationResult.isSuccess()) {
            Entities.getTitles().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<Title>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Title>> list() {
        log.info("Get list of titles");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Title[]> operationResult = new OperationResult<>(response, Title[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            log.error("Unable to get list of titles");
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<Title> update(Title entity) {
        log.info("Update title id:" + entity.getId() + " name:" + entity.getDisplayName());
        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<Title> operationResult = new OperationResult<>(response, Title.class);
        if (operationResult.isSuccess()) {
            Entities.getTitles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult<Title> view(String id) {
        log.info("Get title by id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.view(id));

        OperationResult<Title> operationResult = new OperationResult<>(response, Title.class);
        if (operationResult.isSuccess()) {
            Entities.getTitles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }
}
