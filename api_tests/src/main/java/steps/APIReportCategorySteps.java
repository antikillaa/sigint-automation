package steps;

import abs.EntityList;
import http.OperationResult;
import model.ReportCategory;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ReportCategoryService;

@SuppressWarnings("unchecked")
public class APIReportCategorySteps extends APISteps {

    private ReportCategoryService service = new ReportCategoryService();
    private static Logger LOGGER = Logger.getLogger(APIReportCategorySteps.class);

    @When("I send get list of report categories request")
    public void getReportCategoryList() {
        OperationResult<EntityList<ReportCategory>> result = service.list();
        context.put("reportCategoryEntityList", result.getResult());
    }

    @Then("Report categories list size more than $count")
    public void reportCategoryListShouldBeMoreThan(String count) {
        EntityList<ReportCategory> reportCategories = context.get("reportCategoryEntityList", EntityList.class);

        Assert.assertTrue(reportCategories.getEntities().size() > 0);
    }

}
