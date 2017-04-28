package services;

import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.TagRequest;
import model.Result;
import model.Tag;
import model.TagSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

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
    public OperationResult<List<Tag>> list(SearchFilter filter) {
        log.info("Search list of tags, filter: " + JsonConverter.toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        TagSearchResult searchResult = JsonConverter.jsonToObject(response.getMessage(), TagSearchResult.class, "result");
        return new OperationResult<>(response, searchResult.getResult().getEntities());
    }

    public OperationResult<List<Tag>> list() {
        log.info("Get list of tags");
        G4Response response = g4HttpClient.sendRequest(request.list());

        List<Tag> tags = JsonConverter.jsonToObjectsList(response.getMessage(), Tag[].class, "result");
        return new OperationResult<>(response, tags);
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
