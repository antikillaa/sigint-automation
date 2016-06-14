package steps;

import abs.EntityList;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.DuSubscriberEntry;
import model.phonebook.DuSubscriberFilter;
import model.phonebook.DuSubscriberUploadResult;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.DuSubscriberService;


public class APIDuSubscriberSteps extends APISteps {

    private Logger log = Logger.getLogger(APIPhonebookSteps.class);
    private DuSubscriberService service = new DuSubscriberService();


    @When("I send upload DuSubscriberEntry request with all fields")
    public void sendUploadDuSubscriberEntryRequest() throws NullReturnException {
        DuSubscriberEntry duSubscriberEntry = new DuSubscriberEntry().generate();

        log.info("entry:" + JsonCoverter.toJsonString(duSubscriberEntry));
        int responseCode = service.add(duSubscriberEntry);

        context.put("code", responseCode);
        context.put("duSubscriberEntry", duSubscriberEntry);
    }

    @Then("DuSubscriberEntry upload result is successful")
    public void checkDuSubscriberUploadResults() {
        log.info("Checking DuSubscriberEntry upload result");
        DuSubscriberUploadResult uploadResult = context.get("uploadResult", DuSubscriberUploadResult.class);

        if (uploadResult.getNumEntries() != 1 && uploadResult.getFailedEntries() > 0) {
            log.error("DuSubscriberEntry upload result is not correct!");
            throw new AssertionError("DuSubscriberEntry upload result is not correct!");
        }
    }

    @When("I send search DuSubscribers by $criteria and value $value")
    public void sendSearchDuSubscriberListByCriteriaAndValue(String criteria, String value) throws NullReturnException {
        log.info("Searching DuSubscribers by criteria:" + criteria + " and value:" + value);
        DuSubscriberEntry duSubscriberEntry = context.get("duSubscriberEntry", DuSubscriberEntry.class);

        if (criteria.toLowerCase().equals("address")) {
            value = value.equals("random") ? duSubscriberEntry.getAddress() : value;
        } else if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? duSubscriberEntry.getName() : value;
        } else if (criteria.toLowerCase().equals("phonenumber")) {
            value = value.equals("random") ? duSubscriberEntry.getPhoneNumber() : value;
        } else {
            throw new AssertionError("Unknown filter type");
        }

        DuSubscriberFilter searchFilter = new DuSubscriberFilter().filterBy(criteria, value);
        log.info("search filter: " + JsonCoverter.toJsonString(searchFilter));
        EntityList<DuSubscriberEntry> duSubscriberList = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", duSubscriberList);
    }

    @Then("DuSubscriber search result are correct")
    public void searchDuSubscriberResultsCorrect() throws NullReturnException {
        log.info("Checking if duSubscriber search result is correct");
        DuSubscriberFilter searchFilter = context.get("searchFilter", DuSubscriberFilter.class);
        EntityList<DuSubscriberEntry> searchResults = context.get("searchResult", EntityList.class);

        if (searchResults.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        }
        for (DuSubscriberEntry entry : searchResults) {
            log.info("Search result: " + JsonCoverter.toJsonString(entry));
            Assert.assertTrue(searchFilter.filter(entry));
        }
    }

    @Then("Searched DuSubscriber Entry $critera list")
    public void checkDuSubscriberInResults(String criteria) throws NullReturnException {
        DuSubscriberEntry entry = context.get("duSubscriberEntry", DuSubscriberEntry.class);
        EntityList<DuSubscriberEntry> entityList = context.get("searchResult", EntityList.class);

        log.info("Checking if duSubscriber entry " + criteria + " list");
        Boolean contains = false;
        for (DuSubscriberEntry entity : entityList) {
            if (checkEntry(entry, entity)) {
                contains = true;
                break;
            }
        }

        if (criteria.toLowerCase().equals("in")) {
            Assert.assertTrue(contains);
        } else if (criteria.toLowerCase().equals("not in")) {
            Assert.assertFalse(contains);
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    private boolean checkEntry(DuSubscriberEntry entry, DuSubscriberEntry entity) {
        return Verify.isTrue(Conditions.equals.elements(entry.getPhoneNumber(), entity.getPhoneNumber())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getFirstName(), entity.getFirstName())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getMiddleName(), entity.getMiddleName())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getLastName(), entity.getLastName())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getName(), entity.getName())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getCity(), entity.getCity())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getPoBox(), entity.getPoBox())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getAddress(), entity.getAddress())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getSourceId(), entity.getSourceId())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getTitle(), entity.getTitle())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getNationality(), entity.getNationality())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getVisaType(), entity.getVisaType())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getVisaNumber(), entity.getVisaNumber())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getIdType(), entity.getIdType())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getIdNumber(), entity.getIdNumber())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getStatus(), entity.getStatus())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getCustomerType(), entity.getCustomerType())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getCustomerCode(), entity.getCustomerCode())) &&
                Verify.isTrue(Conditions.equals.elements(entry.getServiceType(), entity.getServiceType()));
    }

    @When("I send get DuSubscriber Entry request")
    public void sendGetDuSubscriberEntryRequest() {
        EntityList<DuSubscriberEntry> searchResults = context.get("searchResult", EntityList.class);
        DuSubscriberEntry etalonEntry = searchResults.getLatest();

        DuSubscriberEntry entry = service.view(etalonEntry.getId());

        context.put("duSubscriberEntry", entry);
        context.put("etalonEntry", etalonEntry);
    }

    @Then("DuSubscriber Entry is correct")
    public void checkDuSubscriberEntryIsCorrect() {
        DuSubscriberEntry entry = context.get("duSubscriberEntry", DuSubscriberEntry.class);
        DuSubscriberEntry etalonEntry = context.get("etalonEntry", DuSubscriberEntry.class);

        Verify.shouldBe(Conditions.equals.elements(entry, etalonEntry));
    }

}
