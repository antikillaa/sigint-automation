package services;

import abs.EntityList;
import abs.SearchFilter;
import http.G4HttpClient;
import http.G4Response;
import http.requests.CategoriesRequest;
import json.JsonCoverter;
import model.CategoryListResult;
import model.ReportCategory;
import org.apache.log4j.Logger;

import java.util.List;

public class CategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(CategoryService.class);
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

        CategoriesRequest request = new CategoriesRequest();
        G4Response response = g4HttpClient.sendRequest(request);

        CategoryListResult result = JsonCoverter.fromJsonToObject(response.getMessage(), CategoryListResult.class);
        if (result != null) {
            log.debug("Size of categories list: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Can not get report categories");
        }
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
