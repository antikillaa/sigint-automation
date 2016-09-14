package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.properties.G4Properties;
import http.G4Response;
import http.client.G4Client;
import http.requests.CategoriesRequest;
import json.JsonCoverter;
import model.CategoryListResult;
import model.ReportCategory;
import org.apache.log4j.Logger;

import java.util.List;

public class CategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(CategoryService.class);
    private static G4Client g4Client = new G4Client();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

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

    public List<ReportCategory> list() {
        log.info("Get categories...");
        CategoriesRequest request = new CategoriesRequest();

        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());

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
