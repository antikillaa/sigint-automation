package steps;

import http.OperationResult;
import model.ProfileEntity;
import model.ProfileEntitySearchFilter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordsSearchService;

import java.util.List;

public class APIRecordsSearchSteps extends APISteps {

    private static RecordsSearchService service = new RecordsSearchService();

    @When("I send records search request, keywords: $keywords, pageSize $pageSize")
    public void recordSearch(String keywords, String pageSize) {

        ProfileEntitySearchFilter filter = new ProfileEntitySearchFilter();
        filter.setPageSize(Integer.valueOf(pageSize));
        filter.setKeywords(keywords);

        OperationResult<List<ProfileEntity>> operationResult = service.list(filter);
        List<ProfileEntity> profileEntities = operationResult.getEntity();

        context.put("ProfileList", profileEntities);
    }

    @Then("Profile enlity list size $criteria than $size")
    public void profileListShouldBeLess(String criteria, String size) {
        List<ProfileEntity> entities = context.get("ProfileList", List.class);

        if (criteria.equals("more")) {
            Assert.assertTrue(entities.size() > Integer.valueOf(size));
        } else if (criteria.equals("less")) {
            Assert.assertTrue(entities.size() < Integer.valueOf(size));
        }
    }
}
