package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.ReportCategoriesRequest;
import model.ReportCategory;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

public class ReportCategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(ReportCategoryService.class);

    @Override
    public OperationResult<ReportCategory> add(ReportCategory entity) {
        log.info("Report category creating");

        ReportCategoriesRequest request = new ReportCategoriesRequest().create(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        ReportCategory reportCategory = JsonConverter.readEntityFromResponse(response, ReportCategory.class, "result");
        OperationResult<ReportCategory> operationResult = new OperationResult<>(response, reportCategory);
        if (operationResult.isSuccess()) {
            Entities.getReportCategories().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<ReportCategory> remove(ReportCategory entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<EntityList<ReportCategory>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    /**
     * GET list of Report Category
     * @return list of ReportCategory
     */
    public OperationResult<EntityList<ReportCategory>> list() {
        log.info("Get list of categories...");

        ReportCategoriesRequest request = new ReportCategoriesRequest();
        G4Response response = g4HttpClient.sendRequest(request);

        List<ReportCategory> reportCategories =
                JsonConverter.readEntitiesFromResponse(response, ReportCategory[].class, "result");

        return new OperationResult<>(response, new EntityList<>(reportCategories));
    }

    @Override
    public OperationResult<ReportCategory> update(ReportCategory entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<ReportCategory> view(String id) {
        throw new NotImplementedException();
    }
}
