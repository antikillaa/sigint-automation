package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
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
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @When("I send create a new responsibility request")
    public void createNewResponsibility() {
        Responsibility responsibility = createRandomResponsibility();
        context.put("responsibility", responsibility);

        responsibilityService.add(responsibility);
    }

    private static Responsibility createRandomResponsibility() {
        Responsibility responsibility = objectInitializer.randomEntity(Responsibility.class);

        int maxPermissionsNumber = 10;
        List<String> permissionNames = getRandomListOfPermissionNames(maxPermissionsNumber);
        responsibility.setPermissions(permissionNames);

        return responsibility;
    }

    private static List<String> getRandomListOfPermissionNames(Integer maxNumber) {
        List<Permission> permissions = context.get("permissionList", List.class);
        if (permissions == null || permissions.isEmpty()) {
            permissions = permissionService.list().getEntity();
        }

        // get random collection of permission
        int minNumber = 1;
        List<Permission> randomPermissionList = RandomGenerator
                .getRandomItemsFromList(permissions, RandomGenerator.generateRandomInteger(minNumber, maxNumber));

        List<String> permissionNames = new ArrayList<>();
        randomPermissionList = randomPermissionList.stream()
                .peek((permission) -> permissionNames.add(permission.getName()))
                .collect(Collectors.toList());

        return permissionNames;
    }

    @Then("Responsibility is correct")
    public void createdResponsibilityShoudBeCorrect(){
        Responsibility responsibility = context.get("responsibility", Responsibility.class);
        Responsibility createdResponsibility = Entities.getResponsibilities().getLatest();

        Assert.assertEquals(createdResponsibility.getDisplayName(), responsibility.getDisplayName());
        Assert.assertFalse(createdResponsibility.getIsDeleted());
        Assert.assertEquals(createdResponsibility.getDescription(), responsibility.getDescription());
        Verify.shouldBe(Conditions.equals(createdResponsibility.getPermissions(), responsibility.getPermissions()));
    }

    @When("I send delete responsibility request")
    public void deleteResponsibility() {
        Responsibility responsibility = Entities.getResponsibilities().getLatest();
        context.put("responsibility", responsibility);

        responsibilityService.remove(responsibility);
    }

    @Then("Responsibility is $criteria the list")
    public void checkResponsibilityInList(String criteria) {
        List<Responsibility> responsibilities = context.get("responsibilityList", List.class);
        Responsibility responsibility = context.get("responsibility", Responsibility.class);

        boolean matched = responsibilities.stream().anyMatch(
                (r -> r.getDisplayName().equals(responsibility.getDisplayName())));

        if (criteria.equals("in")) {
            Assert.assertTrue(matched);
        } else {
            Assert.assertFalse(matched);
        }
    }

    @When("I send update responsibility request")
    public void updateResponsibility() {
        Responsibility responsibility = Entities.getResponsibilities().getLatest();

        Responsibility updatedResponsibility = createRandomResponsibility();
        updatedResponsibility.setId(responsibility.getId());
        context.put("responsibility", updatedResponsibility);

        responsibilityService.update(updatedResponsibility);
    }
}
