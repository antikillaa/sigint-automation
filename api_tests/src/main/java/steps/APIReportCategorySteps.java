package steps;

import model.entities.Entities;
import json.JsonConverter;
import http.OperationResult;
import model.ReportCategory;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.ReportCategoryService;

import java.util.List;

@SuppressWarnings("unchecked")
public class APIReportCategorySteps extends APISteps {

    private static final Logger log = Logger.getLogger(APIReportCategorySteps.class);
    private ReportCategoryService reportCategoryService = new ReportCategoryService();

    @When("I send update report category request")
    public void updateReportCategory() {
        ReportCategory createdCategory = Entities.getReportCategories().getLatest();
        ReportCategory updatedReportCategory = objectInitializer.randomEntity(ReportCategory.class);
        updatedReportCategory.setId(createdCategory.getId());
        updatedReportCategory.setOrder(createdCategory.getOrder());

        OperationResult<ReportCategory> result = reportCategoryService.update(updatedReportCategory);

        context.put("reportCategory", updatedReportCategory);
    }

    @When("I send get list of report categories request")
    public void getReportCategoryList() {
        OperationResult<List<ReportCategory>> result = reportCategoryService.list();
        context.put("reportCategoryList", result.getEntity());
    }

    @Then("Report categories list size more than $count")
    public void reportCategoryListShouldBeMoreThan(final String count) {
        int minSize = Integer.valueOf(count);
        List<ReportCategory> reportCategories =
                context.get("reportCategoryList", List.class);

        Assert.assertTrue(reportCategories.size() > minSize);
    }

    @When("I create new report category")
    public void createReportCategory() {
        ReportCategory reportCategory = objectInitializer.randomEntity(ReportCategory.class);
        reportCategoryService.add(reportCategory);
        context.put("reportCategory", reportCategory);
    }

    @Then("Report category is correct")
    public void reportCategoryIsCorrect() {
        ReportCategory createdCategory = Entities.getReportCategories().getLatest();
        ReportCategory generatedCategory = context.get("reportCategory", ReportCategory.class);

        log.debug("requested: " + JsonConverter.toJsonString(generatedCategory));
        log.debug("created: " + JsonConverter.toJsonString(createdCategory));

        Assert.assertEquals("hidden mismatch", generatedCategory.getHidden(), createdCategory.getHidden());
        Assert.assertEquals("option values mismatch", generatedCategory.getValues(), createdCategory.getValues());
        Assert.assertEquals("category name mismatch", generatedCategory.getName(), createdCategory.getName());
    }

    @Then("Report category is marked as deleted")
    public void reportCategoryIsDeleted() {
        ReportCategory deletedCategory = context.get("deletedReportCategory", ReportCategory.class);
        OperationResult<ReportCategory> result = reportCategoryService.view(deletedCategory.getId());
        Assert.assertEquals("report category should be deleted",
            true, result.getEntity().getDeleted());
    }

    @When("I send delete report category request")
    public void deleteReportCategory() {
        ReportCategory categoryToDelete = Entities.getReportCategories().getLatest();
        reportCategoryService.remove(categoryToDelete);
        context.put("deletedReportCategory", categoryToDelete);
    }
}
