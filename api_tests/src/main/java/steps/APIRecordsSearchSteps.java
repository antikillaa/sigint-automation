package steps;

import abs.EntityList;
import http.OperationResult;
import model.ProfileEntity;
import model.RecordsSearchFilter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordsSearchService;

public class APIRecordsSearchSteps extends APISteps {

    private static RecordsSearchService service = new RecordsSearchService();

    @When("I send records search request, keywords: $keywords, pageSize $pageSize")
    public void recordSearch(String keywords, String pageSize) {

        RecordsSearchFilter filter = new RecordsSearchFilter();
        filter.setPageSize(Integer.valueOf(pageSize));
        filter.setKeywords(keywords);

        OperationResult<EntityList<ProfileEntity>> operationResult = service.list(filter);
        EntityList<ProfileEntity> profileEntities = operationResult.getEntity();

        context.put("ProfileEntityList", profileEntities);
    }

    @Then("Profile enlity list size $criteria than $size")
    public void profileEntityListShouldBeLess(String criteria, String size) {
        EntityList<ProfileEntity> entities = context.get("ProfileEntityList", EntityList.class);

        if (criteria.equals("more")) {
            Assert.assertTrue(entities.size() > Integer.valueOf(size));
        } else if (criteria.equals("less")) {
            Assert.assertTrue(entities.size() < Integer.valueOf(size));
        }
    }
}
