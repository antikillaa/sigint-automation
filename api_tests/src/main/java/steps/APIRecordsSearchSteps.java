package steps;

import http.OperationResult;
import model.CBEntity;
import model.CBSearchFilter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.SearchService;

import java.util.List;

@SuppressWarnings("unchecked")
public class APIRecordsSearchSteps extends APISteps {

    private static SearchService service = new SearchService();

    @When("I send CB search request - sourceCategory:$source, query:$query, pageSize:$pageSize")
    public void recordSearch(String source, String query, String pageSize) {

        CBSearchFilter filter = new CBSearchFilter(source, query, pageSize, "0");

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> profileEntities = operationResult.getEntity();

        context.put("ProfileList", profileEntities);
    }

    @Then("Profile entity list size $criteria than $size")
    public void profileListShouldBeLess(String criteria, String size) {
        List<CBEntity> entities = context.get("ProfileList", List.class);

        switch (criteria) {
            case "more":
                Assert.assertTrue(
                        "Expected list size " + criteria + " than " + size + ", but was:" + entities.size(),
                        entities.size() > Integer.valueOf(size));
                break;
            case "less":
                Assert.assertTrue(
                        "Expected list size " + criteria + " than " + size + ", but was:" + entities.size(),
                        entities.size() < Integer.valueOf(size));
                break;
            default:
                throw new AssertionError("Unknown criteria value, expected:'less' or 'more', but was:" + criteria);
        }
    }
}
