package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.RecordCategoriesRequest;
import json.JsonConverter;
import model.RecordCategory;
import model.RecordCategoryListResult;
import model.Result;
import org.apache.log4j.Logger;
import org.junit.Assert;
import utils.Parser;

import java.util.List;

public class RecordCategoryService implements EntityService<RecordCategory> {

    private Logger logger = Logger.getLogger(RecordCategoryService.class);
    private G4HttpClient g4HttpClient = new G4HttpClient();
    private RunContext context = RunContext.get();

    /**
     * Add a new one record-category.
     *
     * @param entity record-category for payload
     * @return HTTP status code
     */
    @Override
    public int add(RecordCategory entity) {
        logger.info("Add new one record category..");
        logger.info(Parser.entityToString(entity));

        RecordCategoriesRequest request = new RecordCategoriesRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonConverter.readEntityFromResponse(response, Result.class);
        context.put("resultMessage", result.getResult());

        if (result.getResult().equals("ok")) {
            Entities.getRecordCategories().addOrUpdateEntity(entity);
        }
        return response.getStatus();
    }
    
    public void addAll(List<RecordCategory> catList) {
        
        for (RecordCategory category: catList) {
            RecordCategoriesRequest request = new RecordCategoriesRequest().add(category);
            G4Response response = g4HttpClient.sendRequest(request);
    
            Result result = JsonConverter.readEntityFromResponse(response, Result.class);
            Assert.assertTrue(result.getResult().equals("ok"));
        }
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
        logger.info("Updating record-category id:" + entity.getId());
        logger.debug(Parser.entityToString(entity));

        RecordCategoriesRequest request = new RecordCategoriesRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonConverter.readEntityFromResponse(response, Result.class);
        context.put("resultMessage", result.getResult());

        if (result.getResult().equals("ok")) {
            Entities.getRecordCategories().addOrUpdateEntity(entity);
        }
        return response.getStatus();
    }

    /**
     * Get record-category by id
     *
     * @param id id of entity
     * @return RecordCategory
     */
    @Override
    public RecordCategory view(String id) {
        RecordCategoriesRequest request = new RecordCategoriesRequest().view(id);

        G4Response response = g4HttpClient.sendRequest(request);

        RecordCategory recordCategory = JsonConverter.readEntityFromResponse(response, RecordCategory.class, "result");
        if (response.getStatus() == 200) {
            Entities.getRecordCategories().addOrUpdateEntity(recordCategory);
        }

        return recordCategory;
    }
}
