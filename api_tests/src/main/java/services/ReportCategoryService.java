package services;

import abs.EntityList;
import abs.SearchFilter;
import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.ReportCategoriesRequest;
import model.ReportCategory;
import model.ReportCategoryListResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class ReportCategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(ReportCategoryService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    @Override
    public OperationResult<ReportCategory> add(ReportCategory entity) {
        throw new NotImplementedException();
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
    public OperationResult<ReportCategoryListResult> list() {
        log.info("Get list of categories...");

        ReportCategoriesRequest request = new ReportCategoriesRequest();
        G4Response response = g4HttpClient.sendRequest(request);
        return new OperationResult<>(response, ReportCategoryListResult.class);
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
