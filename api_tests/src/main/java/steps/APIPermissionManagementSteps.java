package steps;

import http.OperationResult;
import model.Permission;
import model.Responsibility;
import model.Title;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.PermissionService;
import services.ResponsibilityService;
import services.TitleService;

import java.util.List;

public class APIPermissionManagementSteps extends APISteps {

    private static PermissionService permissionService = new PermissionService();
    private static ResponsibilityService responsibilityService = new ResponsibilityService();
    private static TitleService titleService = new TitleService();

    @When("I send get list of permission request")
    public void getListOfPermission() {
        OperationResult<List<Permission>> operationResult = permissionService.list();
        context.put("permissionList", operationResult.getEntity());
    }

    @Then("Permission list size more than $size")
    public void permissionListSizeShouldBeMoreThan(String size) {
        List<Permission> permissions = context.get("permissionList", List.class);
        Assert.assertTrue(permissions.size() > Integer.valueOf(size));
    }

    @When("I send get list of responsibilities request")
    public void getListOfResponsibilities() {
        OperationResult<List<Responsibility>> operationResult = responsibilityService.list();
        context.put("responsibilityList", operationResult.getEntity());
    }

    @Then("Responsibilities list size more than $size")
    public void responsibilityListSizeShouldBeMoreThan(String size) {
        List<Responsibility> responsibilities = context.get("responsibilityList", List.class);
        Assert.assertTrue(responsibilities.size() > Integer.valueOf(size));
    }

    @When("I send get list of titles request")
    public void getListOfTitles() {
        OperationResult<List<Title>> operationResult = titleService.list();
        context.put("titleList", operationResult.getEntity());
    }

    @Then("Titles list size more than $size")
    public void titleListSizeShouldBeMoreThan(String size) {
        List<Title> titles = context.get("titleList", List.class);
        Assert.assertTrue(titles.size() > Integer.valueOf(size));
    }
}
