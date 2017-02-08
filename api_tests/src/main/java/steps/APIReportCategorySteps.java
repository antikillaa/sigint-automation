package steps;

import abs.EntityList;
import app_context.entities.Entities;
import http.OperationResult;
import http.OperationsResults;
import model.ReportCategory;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ReportCategoryService;

@SuppressWarnings("unchecked")
public class APIReportCategorySteps extends APISteps {

    private static final Logger log = Logger.getLogger(APIReportCategorySteps.class);
    private ReportCategoryService reportCategoryService = new ReportCategoryService();

    @When("I send get list of report categories request")
    public void getReportCategoryList() {
        OperationResult<EntityList<ReportCategory>> result = reportCategoryService.list();
        context.put("reportCategoryEntityList", result.getResult());
    }

    @Then("Report categories list size more than $count")
    public void reportCategoryListShouldBeMoreThan(String count) {
        EntityList<ReportCategory> reportCategories =
                context.get("reportCategoryEntityList", EntityList.class);

        Assert.assertTrue(reportCategories.getEntities().size() > 0);
    }

    @When("I create new report category")
    public void createReportCategory() {
        ReportCategory reportCategory = objectInitializer.randomEntity(ReportCategory.class);

        OperationResult<ReportCategory> operationResult = reportCategoryService.add(reportCategory);
        OperationsResults.setResult(operationResult);

        context.put("reportCategory", operationResult.getResult());
    }

    @Then("Report category is correct")
    public void reportCategoryIsCorrect() {
        ReportCategory savedCategory = Entities.getReportCategories().getLatest();
        ReportCategory responcedCategory = context.get("reportCategory", ReportCategory.class);

        Assert.assertEquals("hidden mismatch", savedCategory.getHidden(), responcedCategory.getHidden());
        Assert.assertEquals("option values mismatch", savedCategory.getValues(), responcedCategory.getValues());
        Assert.assertEquals("category name mismatch", savedCategory.getName(), responcedCategory.getName());
    }
}
