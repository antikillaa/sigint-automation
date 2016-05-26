package steps;

import abs.EntityList;
import model.AppContext;
import model.Phonebook;
import model.phonebook.PhonebookSearchFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.PhonebookService;

public class APIPhonebookSteps {

    private Logger log = Logger.getLogger(APIPhonebookSteps.class);
    private AppContext context = AppContext.getContext();
    private PhonebookService service = new PhonebookService();


    @When("I send search phonebooks list with page $page pagesize $pageSize request")
    public void searchPhonebook(int page, int pageSize) {
        PhonebookSearchFilter searchFilter = new PhonebookSearchFilter().setPage(page).setPageSize(pageSize);
        EntityList<Phonebook> phonebookList = service.list(searchFilter);

        context.putToRunContext("searchFilter", searchFilter);
        context.putToRunContext("searchResult", phonebookList);
    }

    @When("I send create Phonebook Entry request with all fields")
    public void createPhonebookEntry() {
        Phonebook phonebook = new Phonebook().generate();
        int responseCode = service.add(phonebook);

        context.putToRunContext("code", responseCode);
        context.putToRunContext("phonebook", phonebook);
        if (responseCode == 200) {
            checkPhonebookEntry();
        }
    }

    private void checkPhonebookEntry() {
        log.info("Verifying if Phonebook Entry is correct");
        Phonebook etalon = context.getFromRunContext("phonebook", Phonebook.class);
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

}
