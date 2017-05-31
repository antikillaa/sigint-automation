package steps;


import conditions.Conditions;
import conditions.Verify;
import data_generator.DataGenerator;
import http.OperationResult;
import java.util.List;
import model.Whitelist;
import model.entities.Entities;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.WhitelistService;
import utils.RandomGenerator;

public class APIWhitelistSteps extends APISteps {

    private static WhitelistService service = new WhitelistService();

    @When("I send get list of whitelist entries request")
    public void getListOfWhitelistEntries() {
        OperationResult<List<Whitelist>> operationResult = service.list();
        context.put("WhitelistEntitiesList", operationResult.getEntity());
    }

    @Then("Whitelists list size more then $count")
    public void whitelistsListShouldBeMoreThen(final String count) {
        int minSize = Integer.valueOf(count);
        List<Whitelist> whitelists = context.get("WhitelistEntitiesList", List.class);
        Assert.assertTrue(whitelists.size() > minSize);
    }

    @When("I send create new whitelist entry request")
    public void createWhitelistEntry() {
        Whitelist whitelist = objectInitializer.randomEntity(Whitelist.class);
        service.add(whitelist);

        context.put("whitelist", whitelist);
    }

    @Then("Whitelist entry is $criteria common list")
    public void whitelistEntryInCommonList(String criteria) {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);
        List<Whitelist> whitelists = context.get("WhitelistEntitiesList", List.class);
        boolean founded = false;
        for (Whitelist elem : whitelists) {
            if (elem.getId().equals(whitelist.getId())) {
                founded = true;
                break;
            }
        }
        if (criteria.equals("in")) {
            Verify.shouldBe(Conditions.isTrue.element(founded));
        } else {
            Verify.shouldNotBe(Conditions.isTrue.element(founded));
        }
    }


    @Then("Whitelist entry is correct")
    public void whitelistEntryValidation() {
        Whitelist fromContext = context.get("whitelist", Whitelist.class);
        Whitelist latest = Entities.getWhitelists().getLatest();
        Assert.assertEquals("type mismatch", fromContext.getType(), latest.getType());
        Assert.assertEquals("identifier mismatch", fromContext.getIdentifier(), latest.getIdentifier());
        Assert.assertEquals("description mismatch", fromContext.getDescription(), latest.getDescription());
    }

    @When("I get random whitelist entry from list")
    public void selectRandomWhitelistEntry() {
        List<Whitelist> whitelists = context.get("WhitelistEntitiesList", List.class);
        Whitelist whitelist = RandomGenerator.getRandomItemFromList((whitelists)); // whitelists.getLatest();
        Verify.shouldBe(Conditions.isTrue.element(whitelist != null));
        context.put("whitelist", whitelist);
    }

    @When("I send view whitelist entry request")
    public void viewWhitelistEntry() {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);

        OperationResult<Whitelist> operationResult = service.view(whitelist.getId());
        Verify.shouldBe(Conditions.isTrue.element(operationResult.getEntity() != null));

        context.put("whitelist", operationResult.getEntity());
    }

    @When("I send update whitelist entry request")
    public void updateWhitelistEntryRequest() {
        Whitelist whitelist = Entities.getWhitelists().getLatest();
        DataGenerator dataGenerator = new DataGenerator(Whitelist.class);
        Whitelist generatedWhitelist = (Whitelist) dataGenerator.produce();
        generatedWhitelist.setId(whitelist.getId());
        OperationResult operationResult = service.update(generatedWhitelist);
        Verify.shouldBe(Conditions.isTrue.element(operationResult.isSuccess()));
        context.put("whitelist", generatedWhitelist);
    }

    @When("I send delete whitelist entry request")
    public void deleteWhitelistEntryRequest() {
        Whitelist whitelist = Entities.getWhitelists().getLatest();
        service.remove(whitelist);

        context.put("whitelist", whitelist);
    }

}
