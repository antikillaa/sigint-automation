package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.RecordCategoriesRequest;
import model.RecordCategory;
import model.RecordCategoryListResult;
import model.Result;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;

public class RecordCategoryService implements EntityService<RecordCategory> {

    private Logger logger = Logger.getLogger(RecordCategoryService.class);
    private G4HttpClient g4HttpClient = new G4HttpClient();

    /**
     * Add a new one record-category.
     *
     * @param entity record-category for payload
     * @return HTTP status code
     */
    @Override
    public OperationResult<RecordCategory> add(RecordCategory entity) {
        logger.info("Add new one record category..");
        logger.info(Parser.entityToString(entity));

        RecordCategoriesRequest request = new RecordCategoriesRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<RecordCategory> operationResult = new OperationResult<>(response, entity);
        
        if (operationResult.isSuccess()) {
            Entities.getRecordCategories().addOrUpdateEntity(entity);
        }
        return operationResult;
    }
    
    public List<OperationResult> addAll(List<RecordCategory> catList) {
        List<OperationResult> operationResults = new ArrayList<>();
        for (RecordCategory category: catList) {
            RecordCategoriesRequest request = new RecordCategoriesRequest().add(category);
            G4Response response = g4HttpClient.sendRequest(request);
            OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
            operationResults.add(operationResult);
        }
        return operationResults;
    }

    @Override
    public OperationResult remove(RecordCategory entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<EntityList<RecordCategory>> list(SearchFilter filter) { throw new NotImplementedException();
    }

    /**
     * Get list of record-categories
     *
     * @return list of record-categories
     */
    public OperationResult<EntityList<RecordCategory>> list() {
        RecordCategoriesRequest request = new RecordCategoriesRequest().list();

        G4Response response = g4HttpClient.sendRequest(request);
        
        RecordCategoryListResult listResult =
                JsonConverter.readEntityFromResponse(response, RecordCategoryListResult.class);
        EntityList<RecordCategory> recordCategories = listResult.getResult();
        return new OperationResult<>(response, recordCategories);
    }

    @Override
    public OperationResult update(RecordCategory entity) {
        logger.info("Updating record-category id:" + entity.getId());
        logger.debug(Parser.entityToString(entity));

        RecordCategoriesRequest request = new RecordCategoriesRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<RecordCategory> operationResult = new OperationResult<>(response);
        if (operationResult.isSuccess()) {
            Entities.getRecordCategories().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    /**
     * Get record-category by id
     *
     * @param id id of entity
     * @return RecordCategory
     */
    @Override
    public OperationResult<RecordCategory> view(String id) {
        RecordCategoriesRequest request = new RecordCategoriesRequest().view(id);

        G4Response response = g4HttpClient.sendRequest(request);

        RecordCategory recordCategory = JsonConverter.readEntityFromResponse(response, RecordCategory.class, "result");
        OperationResult<RecordCategory> operationResult = new OperationResult<>(response, recordCategory);
        if (operationResult.isSuccess()) {
            Entities.getRecordCategories().addOrUpdateEntity(recordCategory);
        }

        return operationResult;
    }
}
