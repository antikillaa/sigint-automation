package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.Phonebook;
import model.UploadResult;
import model.phonebook.PhonebookSearchFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.PhonebookService;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.isTrue;

public class APIPhonebookSteps extends APISteps {

    private Logger log = Logger.getLogger(APIPhonebookSteps.class);
    private PhonebookService service = new PhonebookService();

    @When("I send create Phonebook Entry request with all fields")
    public void createPhonebookEntry() {
        Phonebook phonebook = getRandomPhonebook();

        int responseCode = service.add(phonebook);

        context.put("code", responseCode);
        context.put("phonebook", phonebook);
    }

    @When("I send update request for created Phonebook Entry")
    public void updatePhonebookEntry() {
        Phonebook phonebook = Entities.getPhonebooks().getLatest();
        Phonebook newPhonebook = getRandomPhonebook();
        newPhonebook.setId(phonebook.getId());

        int responseCode = service.update(newPhonebook);

        context.put("code", responseCode);
        context.put("phonebook", newPhonebook);
    }

    @Then("Phonebook Entry is correct")
    public void checkPhonebookEntry() {
        log.info("Verifying if Phonebook Entry is correct");
        Phonebook etalon = context.get("phonebook", Phonebook.class);
        Phonebook created = Entities.getPhonebooks().getLatest();

        Assert.assertEquals(etalon.getName(), created.getName());
        Assert.assertEquals(etalon.getPhoneNumber(), created.getPhoneNumber());
        Assert.assertEquals(etalon.getCountry(), created.getCountry());
        Assert.assertEquals(etalon.getAddress(), created.getAddress());
        Assert.assertEquals(etalon.getProvider(), created.getProvider());
        Assert.assertEquals(etalon.getLocation(), created.getLocation());
        Assert.assertTrue(created.getId() != null);
        Assert.assertTrue(created.isManualEntry());
    }

    @When("I send delete request for created Phonebook Entry")
    public void deletePhonebookEntry() {
        Phonebook phonebook = Entities.getPhonebooks().getLatest();
        context.put("id", phonebook.getId());

        int responseCode = service.remove(phonebook);

        context.put("code", responseCode);
    }

    @Then("Phonebook Entry was deleted")
    public void checkPhonebookWasDeleted() {
        String id = context.get("id", String.class);
        Phonebook phonebook = service.view(id);

        Assert.assertNull("Phonebook was not deleted", phonebook);
    }

    @When("I get details of created Phonebook Entry")
    public void getPhonebookEntry() {
        Phonebook entity = Entities.getPhonebooks().getLatest();
        Phonebook phonebook = service.view(entity.getId());

        context.put("phonebook", phonebook);
    }

    @When("I search Phonebook Entry by $criteria and value $value")
    public void searchPhonebookbyCriteria(String criteria, String value) throws NullReturnException {
        log.info("Start search phonebook by criteria: " + criteria + ", value: " + value);
        Phonebook phonebook = Entities.getPhonebooks().getLatest();

        if (criteria.toLowerCase().equals("address")) {
            value = value.equals("random") ? phonebook.getAddress() : value;
        } else if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? phonebook.getName() : value;
        } else if (criteria.toLowerCase().equals("country")) {
            String countryName =  value.equals("random") ? phonebook.getCountry() : value;
            value = RandomGenerator.getCountryCode(countryName);
        } else if (criteria.toLowerCase().equals("countrycode")) {
            value = value.equals("random") ? phonebook.getCountryCode() : value;
        } else if (criteria.toLowerCase().equals("phonenumber")) {
            value = value.equals("random") ? phonebook.getPhoneNumber() : value;
        } else if (criteria.toLowerCase().equals("imsi")) {
            value = value.equals("random") ? phonebook.getImsi() : value;
        } else if (criteria.toLowerCase().equals("querystring")) {
            value = value.equals("random") ? phonebook.getPhoneNumber() : value;
        } else {
            throw new AssertionError("Unknown isAppliedToEntity type");
        }

        PhonebookSearchFilter searchFilter = new PhonebookSearchFilter().filterBy(criteria, value);
        log.info("Search isAppliedToEntity: " + JsonCoverter.toJsonString(searchFilter));
        EntityList<Phonebook> phonebookList = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", phonebookList);
    }

    @Then("Search Phonebook results are correct")
    public void searchPhonebookResultsCorrect() {
        log.info("Checking if search phonebook result is correct");
        PhonebookSearchFilter searchFilter = context.get("searchFilter", PhonebookSearchFilter.class);
        EntityList<Phonebook> searchResult = context.get("searchResult", EntityList.class);

        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResult.size());
        }
        for (Phonebook phonebook : searchResult) {
            Assert.assertTrue(String.format("Entity:%s found by filter %s is not correct", phonebook, searchFilter),
                    searchFilter.isAppliedToEntity(phonebook));
        }
    }

    @Then("Searched Phonebook Entry $critera list")
    public void checkPhonebookInResults(String criteria) {
        Phonebook phonebook = Entities.getPhonebooks().getLatest();
        EntityList<Phonebook> list = context.get("searchResult", EntityList.class);
        log.info("Checking if phonebook entry " + criteria + " list");

        Boolean contains = false;
        for(Phonebook entry : list) {
            if (entry.getPhoneNumber().equals(phonebook.getPhoneNumber())) {
                contains = true;
                break;
            }
        }

        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @When("I send upload phonebook request with CSV file containing $count phonebooks")
    public void uploadPhonebookCSVFile(String count) {
        int numPonebooks = Integer.valueOf(count);
        List<Phonebook> phonebooks = getRandomPhoneBooks(numPonebooks);
        int responseCode = service.upload(phonebooks);
        context.put("code", responseCode);
        context.put("uploadedPhonebooks", phonebooks);
        for (Phonebook phonebook : phonebooks){
            Entities.getPhonebooks().addOrUpdateEntity(phonebook);
        }
    }

    @Then("Upload result of $count 'Phonebook' entries is successful")
    public void checkUploadResults(String count) {
        log.info("Checking 'Phonebook' entries upload result");
        UploadResult uploadResult = context.get("uploadResult", UploadResult.class);

        int numEntries = Integer.valueOf(count);
        if (uploadResult.getNumEntries() != numEntries || uploadResult.getFailedEntries() > 0) {
            String errorMessage = "Upload result of Phonebooks entries is not correct!\n" +
                    "Expected: numEntries <" + numEntries + ">, " + "failedEntries <0>\n" +
                    "Actual: numEntries <" + uploadResult.getNumEntries() + ">, " + "failedEntries <" + uploadResult.getFailedEntries() + ">";
            log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
    }
    
    static Phonebook getRandomPhonebook() {
        return (Phonebook)objectInitializer.generateObject(Phonebook.class);
    }
    
    static List<Phonebook> getRandomPhoneBooks(int count) {
        List<Phonebook> phoneBooks = new ArrayList<>();
        for (int i=1; i<=count;i++) {
            phoneBooks.add(getRandomPhonebook());
        }
        return phoneBooks;
    }
}
