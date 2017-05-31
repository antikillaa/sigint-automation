package services;

import model.SearchFilter;
import model.entities.Entities;
import http.G4Response;
import json.JsonConverter;
import http.OperationResult;
import http.requests.TagRequest;
import model.Result;
import model.Tag;
import model.TagSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TagService implements EntityService<Tag> {

    private static Logger log = Logger.getLogger(TagService.class);
    private static TagRequest request = new TagRequest();

    @Override
    public OperationResult<Result> add(Tag entity) {
        log.info("Create tag: " + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.create(entity));

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            Entities.getTags().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Tag entity) {
        log.info("Delete tag, id: " + entity.getId() + " name: " + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            Entities.getTags().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<Tag>> search(SearchFilter filter) {
        log.info("Search list of tags, filter: " + JsonConverter.toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<TagSearchResult> operationResult = new OperationResult<>(response, TagSearchResult.class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, operationResult.getEntity().getResult());
        } else {
            return new OperationResult<>(response);
        }
    }

    @Override
    public OperationResult<List<Tag>> list() {
        log.info("Get list of tags");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Tag[]> operationResult = new OperationResult<>(response, Tag[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    @Override
    public OperationResult<Tag> update(Tag entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Tag> view(String id) {
        throw new NotImplementedException();
    }
}
