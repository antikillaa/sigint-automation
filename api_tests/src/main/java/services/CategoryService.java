package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.CategoriesRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.ReportCategory;
import model.CategoryListResult;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;
import java.util.List;

public class CategoryService implements EntityService<ReportCategory> {

    private Logger log = Logger.getLogger(CategoryService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

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

        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        String jsonResponse = response.readEntity(String.class);

        CategoryListResult result = JsonCoverter.fromJsonToObject(jsonResponse, CategoryListResult.class);
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
