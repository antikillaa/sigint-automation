package steps;

import abs.EntityList;
import conditions.EqualCondition;
import errors.NullReturnException;
import json.JsonCoverter;
import model.EtisalatSubscriberEntry;
import model.phonebook.EtisalatSubscriberFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.EtisalatSubscriberService;
import verifier.Verify;

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
        return new EqualCondition(entry.getPhoneNumber(), entity.getPhoneNumber()).check() &&
                new EqualCondition(entry.getName(), entity.getName()).check() &&
                new EqualCondition(entry.getAddress(), entity.getAddress()).check() &&
                new EqualCondition(entry.getSourceId(), entity.getSourceId()).check() &&
                new EqualCondition(entry.getAction(), entity.getAction()).check() &&
                new EqualCondition(entry.getAccountSuffix(), entity.getAccountSuffix()).check() &&
                new EqualCondition(entry.getPartyId(), entity.getPartyId()).check() &&
                new EqualCondition(entry.getAccountNameArabic(), entity.getAccountNameArabic()).check() &&
                new EqualCondition(entry.getUserIdOrName(), entity.getUserIdOrName()).check() &&
                new EqualCondition(entry.getInstallationBuilding(), entity.getInstallationBuilding()).check() &&
                new EqualCondition(entry.getInstallationFlatNumber(), entity.getInstallationFlatNumber()).check() &&
                new EqualCondition(entry.getInstallationFloor(), entity.getInstallationFloor()).check() &&
                new EqualCondition(entry.getInstallationStreetName(), entity.getInstallationStreetName()).check() &&
                new EqualCondition(entry.getInstallationPlotNumber(), entity.getInstallationPlotNumber()).check() &&
                new EqualCondition(entry.getInstallationMapNumber(), entity.getInstallationMapNumber()).check() &&
                new EqualCondition(entry.getInstallationSector(), entity.getInstallationSector()).check() &&
                new EqualCondition(entry.getInstallationTownCode(), entity.getInstallationTownCode()).check() &&
                new EqualCondition(entry.getInstallationTownName(), entity.getInstallationTownName()).check() &&
                new EqualCondition(entry.getInstallationTownEmirate(), entity.getInstallationTownEmirate()).check() &&
                new EqualCondition(entry.getAddress(), entity.getAddress()).check() &&
                new EqualCondition(entry.getFirstAddressLine(), entity.getFirstAddressLine()).check() &&
                new EqualCondition(entry.getSecondAddressLine(), entity.getSecondAddressLine()).check() &&
                new EqualCondition(entry.getPoBoxNumber(), entity.getPoBoxNumber()).check() &&
                new EqualCondition(entry.getCustomerCategoryCode(), entity.getCustomerCategoryCode()).check() &&
                new EqualCondition(entry.getCustomerCategoryCodeDesc(), entity.getCustomerCategoryCodeDesc()).check() &&
                new EqualCondition(entry.getDateOfInstallation(), entity.getDateOfInstallation()).check() &&
                new EqualCondition(entry.getCountryCode(), entity.getCountryCode()).check() &&
                new EqualCondition(entry.getCountryCodeOriginal(), entity.getCountryCodeOriginal()).check() &&
                new EqualCondition(entry.getCountry(), entity.getCountry()).check() &&
                new EqualCondition(entry.getSubscriberAccountStatusCode(), entity.getSubscriberAccountStatusCode()).check() &&
                new EqualCondition(entry.getSubscriberAccountStatusDesc(), entity.getSubscriberAccountStatusDesc()).check() &&
                new EqualCondition(entry.getProductGroupCode(), entity.getProductGroupCode()).check() &&
                new EqualCondition(entry.getProductGroupDesc(), entity.getProductGroupDesc()).check() &&
                new EqualCondition(entry.getProductCode(), entity.getProductCode()).check() &&
                new EqualCondition(entry.getProductDesc(), entity.getProductDesc()).check() &&
                new EqualCondition(entry.getImsi(), entity.getImsi()).check() &&
                new EqualCondition(entry.getIdentificationTypeCode(), entity.getIdentificationTypeCode()).check() &&
                new EqualCondition(entry.getIdentificationTypeDesc(), entity.getIdentificationTypeDesc()).check() &&
                new EqualCondition(entry.getIdentificationInfo(), entity.getIdentificationInfo()).check() &&
                new EqualCondition(entry.getProvisionedRegionCode(), entity.getProvisionedRegionCode()).check() &&
                new EqualCondition(entry.getProvisionedRegionCodeDesc(), entity.getProvisionedRegionCodeDesc()).check() &&
                new EqualCondition(entry.getCityId(), entity.getCityId()).check() &&
                new EqualCondition(entry.getCityName(), entity.getCityName()).check();
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

        Verify.shouldBe(new EqualCondition(entry, etalonEntry));
    }

}
