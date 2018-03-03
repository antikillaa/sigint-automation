package services;

import app_context.AppContext;
import http.G4Response;
import http.OperationResult;
import http.requests.TagRequest;
import json.JsonConverter;
import model.Result;
import model.SearchFilter;
import model.Tag;
import model.TagSearchResult;
import model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import utils.RandomGenerator;

import java.util.Arrays;
import java.util.List;

public class TagService implements EntityService<Tag> {

    private static Logger log = Logger.getLogger(TagService.class);
    private static TagRequest request = new TagRequest();

    @Override
    public OperationResult<Tag> add(Tag entity) {
        List<String> teamIds = AppContext.get().getLoggedUser().getUser().getParentTeamIds();
        entity.setTeamId(RandomGenerator.getRandomItemFromList(teamIds));

        log.info("Create tag: " + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.create(entity));

        OperationResult<Tag> operationResult = new OperationResult<>(response, Tag.class);
        if (operationResult.isSuccess()) {
            Entities.getTags().addOrUpdateEntity(operationResult.getEntity());
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
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<Tag> view(String id) {
        throw new NotImplementedException("");
    }
}
