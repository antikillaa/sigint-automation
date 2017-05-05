package steps;

import http.OperationResult;
import model.Permission;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.PermissionService;

import java.util.List;

public class APIPermissionManagementSteps extends APISteps {

    private PermissionService permissionService = new PermissionService();

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

}
