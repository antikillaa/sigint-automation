package steps;

import abs.EntityList;
import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.EtisalatSubscriberEntry;
import model.phonebook.EtisalatSubscriberFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.EtisalatSubscriberService;

import static conditions.Conditions.equals;

public class APIEtisalatSubscriberDataSteps extends APISteps {

    private Logger log = Logger.getLogger(APIEtisalatSubscriberDataSteps.class);
    private EtisalatSubscriberService service = new EtisalatSubscriberService();


    @When("I send upload EtisalatSubscriberData entry request with all fields")
    public void sendUploadEtisalatSubscriberDataEntryRequest() throws NullReturnException {
        EtisalatSubscriberEntry entry = new EtisalatSubscriberEntry().generate();
        log.info("Entry:" + JsonCoverter.toJsonString(entry));

        int responseCode = service.add(entry);

        context.put("code", responseCode);
        context.put("etisalatSubscriberEntry", entry);
    }

    @When("I send search EtisalatSubscriberData by $criteria and value $value")
    public void sendSearchEtisalatListByCriteriaAndValue(String criteria, String value) throws NullReturnException {
        log.info("Searching EtisalatSubscriberData by criteria:" + criteria + " and value:" + value);
        EtisalatSubscriberEntry entry = context.get("etisalatSubscriberEntry", EtisalatSubscriberEntry.class);

        if (criteria.toLowerCase().equals("address")) {
            value = value.equals("random") ? entry.getAddress() : value;
        } else if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? entry.getName() : value;
        } else if (criteria.toLowerCase().equals("useridorname")) {
            value = value.equals("random") ? entry.getUserIdOrName() : value;
        } else if (criteria.toLowerCase().equals("accountnamearabic")) {
            value = value.equals("random") ? entry.getAccountNameArabic() : value;
        } else if (criteria.toLowerCase().equals("phonenumber")) {
            value = value.equals("random") ? entry.getPhoneNumber() : value;
        } else if (criteria.toLowerCase().equals("imsi")) {
            value = value.equals("random") ? entry.getImsi() : value;
        } else if (criteria.toLowerCase().equals("firstaddressline")) {
            value = value.equals("random") ? entry.getFirstAddressLine() : value;
        } else if (criteria.toLowerCase().equals("secondaddressline")) {
            value = value.equals("random") ? entry.getSecondAddressLine() : value;
        } else if (criteria.toLowerCase().equals("cityname")) {
            value = value.equals("random") ? entry.getCityName() : value;
        } else {
            throw new AssertionError("Unknown filter type");
        }

        EtisalatSubscriberFilter searchFilter = new EtisalatSubscriberFilter().filterBy(criteria, value);
        log.info("Search filter: " + JsonCoverter.toJsonString(searchFilter));
        EntityList<EtisalatSubscriberEntry> entityList = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", entityList);
    }

    @Then("EtisalatSubscriberData search result are correct")
    public void searchEtisalatResultsCorrect() throws NullReturnException {
        log.info("Checking if etisalatSubscriberData search result is correct");
        EtisalatSubscriberFilter searchFilter = context.get("searchFilter", EtisalatSubscriberFilter.class);
        EntityList<EtisalatSubscriberEntry> searchResults = context.get("searchResult", EntityList.class);

        if (searchResults.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResults.size());
        }
        for (EtisalatSubscriberEntry entry : searchResults) {
            log.info("Checking result: " + JsonCoverter.toJsonString(entry));
            Assert.assertTrue(searchFilter.filter(entry));
        }
    }

    @Then("Searched EtisalatSubscriberData Entry $critera list")
    public void checkEtisalatSubscriberEntryInResults(String criteria) throws NullReturnException {
        EtisalatSubscriberEntry entry = context.get("etisalatSubscriberEntry", EtisalatSubscriberEntry.class);
        EntityList<EtisalatSubscriberEntry> entityList = context.get("searchResult", EntityList.class);

        log.info("Checking if etisalatSubscriberData entry " + criteria + " list");
        Boolean contains = false;
        for (EtisalatSubscriberEntry entity : entityList) {
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

    private boolean checkEntry(EtisalatSubscriberEntry entry, EtisalatSubscriberEntry entity) {
        return Verify.isTrue(equals.elements(entry.getPhoneNumber(), entity.getPhoneNumber())) &&
                Verify.isTrue(equals.elements(entry.getName(), entity.getName())) &&
                Verify.isTrue(equals.elements(entry.getAddress(), entity.getAddress())) &&
                Verify.isTrue(equals.elements(entry.getSourceId(), entity.getSourceId())) &&
                Verify.isTrue(equals.elements(entry.getAction(), entity.getAction())) &&
                Verify.isTrue(equals.elements(entry.getAccountSuffix(), entity.getAccountSuffix())) &&
                Verify.isTrue(equals.elements(entry.getPartyId(), entity.getPartyId())) &&
                Verify.isTrue(equals.elements(entry.getAccountNameArabic(), entity.getAccountNameArabic())) &&
                Verify.isTrue(equals.elements(entry.getUserIdOrName(), entity.getUserIdOrName())) &&
                Verify.isTrue(equals.elements(entry.getInstallationBuilding(), entity.getInstallationBuilding())) &&
                Verify.isTrue(equals.elements(entry.getInstallationFlatNumber(), entity.getInstallationFlatNumber())) &&
                Verify.isTrue(equals.elements(entry.getInstallationFloor(), entity.getInstallationFloor())) &&
                Verify.isTrue(equals.elements(entry.getInstallationStreetName(), entity.getInstallationStreetName())) &&
                Verify.isTrue(equals.elements(entry.getInstallationPlotNumber(), entity.getInstallationPlotNumber())) &&
                Verify.isTrue(equals.elements(entry.getInstallationMapNumber(), entity.getInstallationMapNumber())) &&
                Verify.isTrue(equals.elements(entry.getInstallationSector(), entity.getInstallationSector())) &&
                Verify.isTrue(equals.elements(entry.getInstallationTownCode(), entity.getInstallationTownCode())) &&
                Verify.isTrue(equals.elements(entry.getInstallationTownName(), entity.getInstallationTownName())) &&
                Verify.isTrue(equals.elements(entry.getInstallationTownEmirate(), entity.getInstallationTownEmirate())) &&
                Verify.isTrue(equals.elements(entry.getAddress(), entity.getAddress())) &&
                Verify.isTrue(equals.elements(entry.getFirstAddressLine(), entity.getFirstAddressLine())) &&
                Verify.isTrue(equals.elements(entry.getSecondAddressLine(), entity.getSecondAddressLine())) &&
                Verify.isTrue(equals.elements(entry.getPoBoxNumber(), entity.getPoBoxNumber())) &&
                Verify.isTrue(equals.elements(entry.getCustomerCategoryCode(), entity.getCustomerCategoryCode())) &&
                Verify.isTrue(equals.elements(entry.getCustomerCategoryCodeDesc(), entity.getCustomerCategoryCodeDesc())) &&
                Verify.isTrue(equals.elements(entry.getDateOfInstallation(), entity.getDateOfInstallation())) &&
                Verify.isTrue(equals.elements(entry.getCountryCode(), entity.getCountryCode())) &&
                Verify.isTrue(equals.elements(entry.getCountryCodeOriginal(), entity.getCountryCodeOriginal())) &&
                Verify.isTrue(equals.elements(entry.getCountry(), entity.getCountry())) &&
                Verify.isTrue(equals.elements(entry.getSubscriberAccountStatusCode(), entity.getSubscriberAccountStatusCode())) &&
                Verify.isTrue(equals.elements(entry.getSubscriberAccountStatusDesc(), entity.getSubscriberAccountStatusDesc())) &&
                Verify.isTrue(equals.elements(entry.getProductGroupCode(), entity.getProductGroupCode())) &&
                Verify.isTrue(equals.elements(entry.getProductGroupDesc(), entity.getProductGroupDesc())) &&
                Verify.isTrue(equals.elements(entry.getProductCode(), entity.getProductCode())) &&
                Verify.isTrue(equals.elements(entry.getProductDesc(), entity.getProductDesc())) &&
                Verify.isTrue(equals.elements(entry.getImsi(), entity.getImsi())) &&
                Verify.isTrue(equals.elements(entry.getIdentificationTypeCode(), entity.getIdentificationTypeCode())) &&
                Verify.isTrue(equals.elements(entry.getIdentificationTypeDesc(), entity.getIdentificationTypeDesc())) &&
                Verify.isTrue(equals.elements(entry.getIdentificationInfo(), entity.getIdentificationInfo())) &&
                Verify.isTrue(equals.elements(entry.getProvisionedRegionCode(), entity.getProvisionedRegionCode())) &&
                Verify.isTrue(equals.elements(entry.getProvisionedRegionCodeDesc(), entity.getProvisionedRegionCodeDesc())) &&
                Verify.isTrue(equals.elements(entry.getCityId(), entity.getCityId())) &&
                Verify.isTrue(equals.elements(entry.getCityName(), entity.getCityName()));
    }

    @When("I send get EtisalatSubscriberData Entry request")
    public void getEtisalatSubscriberRequest() {
        EntityList<EtisalatSubscriberEntry> searchResults = context.get("searchResult", EntityList.class);
        EtisalatSubscriberEntry etalonEntry = searchResults.getLatest();

        EtisalatSubscriberEntry entry = service.view(etalonEntry.getId());

        context.put("etisalatSubscriberEntry", entry);
        context.put("etalonEntry", etalonEntry);
    }

    @Then("EtisalatSubscriberData Entry is correct")
    public void checkDuSubscriberEntryIsCorrect() {
        EtisalatSubscriberEntry entry = context.get("etisalatSubscriberEntry", EtisalatSubscriberEntry.class);
        EtisalatSubscriberEntry etalonEntry = context.get("etalonEntry", EtisalatSubscriberEntry.class);

        Verify.shouldBe(equals.elements(entry, etalonEntry));
    }

}
