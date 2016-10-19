package services;

import abs.EntityList;
import abs.SearchFilter;
import http.G4HttpClient;
import http.G4Response;
import http.requests.ReportCategoriesRequest;
import json.JsonConverter;
import model.ReportCategoryListResult;
import model.ReportCategory;
import org.apache.log4j.Logger;

import java.util.List;

public class ReportCategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(ReportCategoryService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    @Override
    public int add(ReportCategory entity) {
        return 0;
    }

    @Override
    public int remove(ReportCategory entity) {
        return 0;
    }

    @Override
    public EntityList<ReportCategory> list(SearchFilter filter) {
        return null;
    }

    /**
     * GET list of Report Category
     * @return list of ReportCategory
     */
    public List<ReportCategory> list() {
        log.info("Get list of categories...");

        ReportCategoriesRequest request = new ReportCategoriesRequest();
        G4Response response = g4HttpClient.sendRequest(request);

        ReportCategoryListResult result = JsonConverter.readEntityFromResponse(response, ReportCategoryListResult.class);
        return result.getResult();
    }

    @Override
    public int update(ReportCategory entity) {
        return 0;
    }

    @Override
    public ReportCategory view(String id) {
        return null;
    }
}
