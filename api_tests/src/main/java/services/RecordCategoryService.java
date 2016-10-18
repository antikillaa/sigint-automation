package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import http.G4HttpClient;
import http.G4Response;
import http.requests.RecordCategoriesRequest;
import json.JsonConverter;
import model.RecordCategory;
import model.RecordCategoryListResult;
import model.Result;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class RecordCategoryService implements EntityService<RecordCategory> {

    Logger logger = Logger.getLogger(RecordCategoryService.class);
    G4HttpClient g4HttpClient = new G4HttpClient();
    RunContext context = RunContext.get();

    /**
     * Add a new one record-category.
     *
     * @param entity record-category for payload
     * @return HTTP status code
     */
    @Override
    public int add(RecordCategory entity) {
        logger.info("Add new one record category..");
        logger.info("Entity: " + Parser.entityToString(entity));

        RecordCategoriesRequest request = new RecordCategoriesRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonConverter.readEntityFromResponse(response, Result.class);
        context.put("resultMessage", result.getResult());

        return response.getStatus();
    }

    @Override
    public int remove(RecordCategory entity) {
        return 0;
    }

    @Override
    public EntityList<RecordCategory> list(SearchFilter filter) {
        return null;
    }

    /**
     * Get list of record-categories
     *
     * @return list of record-categories
     */
    public List<RecordCategory> list() {
        RecordCategoriesRequest request = new RecordCategoriesRequest().list();

        G4Response response = g4HttpClient.sendRequest(request);

        RecordCategoryListResult listResult =
                JsonConverter.readEntityFromResponse(response, RecordCategoryListResult.class);

        return listResult.getResult();
    }

    @Override
    public int update(RecordCategory entity) {
        return 0;
    }

    @Override
    public RecordCategory view(String id) {
        return null;
    }
}
