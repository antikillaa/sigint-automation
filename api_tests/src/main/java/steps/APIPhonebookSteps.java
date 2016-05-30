package steps;

import abs.EntityList;
import errors.NullReturnException;
import model.Phonebook;
import model.phonebook.PhonebookSearchFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.PhonebookService;

public class APIPhonebookSteps extends APISteps {

    private Logger log = Logger.getLogger(APIPhonebookSteps.class);
    private PhonebookService service = new PhonebookService();


    @When("I send search phonebooks list with page $page pagesize $pageSize request")
    public void searchPhonebook(int page, int pageSize) {
        PhonebookSearchFilter searchFilter = new PhonebookSearchFilter().setPage(page).setPageSize(pageSize);

        EntityList<Phonebook> phonebookList = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", phonebookList);
    }

    @When("I send create Phonebook Entry request with all fields")
    public void createPhonebookEntry() throws NullReturnException {
        Phonebook phonebook = new Phonebook().generate();

        int responseCode = service.add(phonebook);

        context.put("code", responseCode);
        context.put("phonebook", phonebook);
    }

    @When("I send update request for created Phonebook Entry")
    public void updatePhonebookEntry() throws NullReturnException {
        Phonebook phonebook = context.entities().getPhonebooks().getLatest();
        Phonebook newPhonebook = phonebook.generate();

        int responseCode = service.update(newPhonebook);

        context.put("code", responseCode);
        context.put("phonebook", newPhonebook);
    }

    @Then("Phonebook Entry is correct")
    public void checkPhonebookEntry() {
        log.info("Verifying if Phonebook Entry is correct");
        Phonebook etalon = context.get("phonebook", Phonebook.class);
        Phonebook created = context.entities().getPhonebooks().getLatest();

        Assert.assertEquals(etalon.getName(), created.getName());
        Assert.assertEquals(etalon.getPhoneNumber(), created.getPhoneNumber());
        Assert.assertEquals(etalon.getCountry(), created.getCountry());
        Assert.assertEquals(etalon.getAddress(), created.getAddress());
        Assert.assertEquals(etalon.getImsi(), created.getImsi());
        Assert.assertEquals(etalon.getProvider(), created.getProvider());
        Assert.assertEquals(etalon.getLocation(), created.getLocation());
        Assert.assertTrue(created.getId() != null);
        Assert.assertTrue(created.isManualEntry());
        Assert.assertTrue(created.get_version() != null);
    }

    @When("I send delete request for created Phonebook Entry")
    public void deletePhonebookEntry() {
        Phonebook phonebook = context.entities().getPhonebooks().getLatest();
        context.put("id", phonebook.getId());

        int responseCode = service.remove(phonebook);

        context.put("code", responseCode);
    }

    @Then("Phonebook Entry was deleted")
    public void checkPhonebookWasDeleted() throws NullReturnException {
        String id = context.get("id", String.class);
        Phonebook phonebook = service.view(id);

        Assert.assertNull("Phonebook was not deleted", phonebook);
    }

    @When("I get details of created Phonebook Entry")
    public void getPhonebookEntry() {
        Phonebook entity = context.entities().getPhonebooks().getLatest();
        Phonebook phonebook = service.view(entity.getId());

        context.put("phonebook", phonebook);
    }

}
