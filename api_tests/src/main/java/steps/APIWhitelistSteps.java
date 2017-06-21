package steps;


import static ingestion.IngestionService.cleanImportDir;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import error_reporter.ErrorReporter;
import http.OperationResult;
import java.util.ArrayList;
import java.util.List;
import json.JsonConverter;
import model.G4File;
import model.ImportResult;
import model.Whitelist;
import model.WhitelistFilter;
import model.entities.Entities;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.WhitelistService;
import utils.RandomGenerator;

public class APIWhitelistSteps extends APISteps {

    private static WhitelistService service = new WhitelistService();

    private static Whitelist getRandomWhitelist() {
        return objectInitializer.randomEntity(Whitelist.class);
    }

    static List<Whitelist> getRandomWhitelists(int count) {
        return objectInitializer.randomEntities(Whitelist.class, count);
    }

    @When("I send get list of whitelist entries request")
    public void getListOfWhitelistEntries() {
        OperationResult<List<Whitelist>> operationResult = service.list();
        context.put("whitelistEntitiesList", operationResult.getEntity());
    }

    @Then("Whitelists list size is more than $count")
    @SuppressWarnings("unchecked")
    public void whitelistsListShouldBeMoreThen(final String count) {
        int minSize = Integer.valueOf(count);
        List<Whitelist> whitelists = context.get("whitelistEntitiesList", List.class);
        log.info("Whitelists list size: " + whitelists.size());

        assertTrue("List size mismatch. Should be > " + minSize, whitelists.size() > minSize);
    }

    @When("I send create new whitelist entry request")
    public void createWhitelistEntry() {
        Whitelist whitelist = getRandomWhitelist();
        context.put("whitelist", whitelist);
        service.add(whitelist);
    }

    @Then("Whitelist entry is $criteria list")
    @SuppressWarnings("unchecked")
    public void whitelistEntryInCommonList(String criteria) {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);
        List<Whitelist> whitelists = context.get("whitelistEntitiesList", List.class);
        boolean contained = false;
        for (Whitelist elem : whitelists) {
            if (elem.getId().equals(whitelist.getId())) {
                contained = true;
                break;
            }
        }
        if (criteria.equals("in")) {
            assertTrue("Whitelist " + whitelist.getId() + " should be in list", contained);
        } else {
            assertFalse("Whitelist " + whitelist.getId() + " should not be listed", contained);
        }
    }


    @Then("Whitelist entry is correct")
    public void whitelistEntryValidation() {
        Whitelist fromContext = context.get("whitelist", Whitelist.class);
        Whitelist latest = Entities.getWhitelists().getLatest();

        assertEquals("type mismatch", fromContext.getType(), latest.getType());
        assertEquals("identifier mismatch", fromContext.getIdentifier(), latest.getIdentifier());
        assertEquals("description mismatch", fromContext.getDescription(), latest.getDescription());

        context.put("whitelist", latest);
    }

    @When("I get random whitelist entry from list")
    @SuppressWarnings("unchecked")
    public void selectRandomWhitelistEntry() {
        List<Whitelist> whitelists = context.get("whitelistEntitiesList", List.class);
        Whitelist whitelist = RandomGenerator.getRandomItemFromList((whitelists));

        assertNotNull(whitelist);
        context.put("whitelist", whitelist);
    }

    @When("I send view whitelist entry request")
    public void viewWhitelistEntry() {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);

        OperationResult<Whitelist> operationResult = service.view(whitelist.getId());
        assertTrue(operationResult.getEntity() != null);

        context.put("whitelist", operationResult.getEntity());
    }

    @When("I send update whitelist entry request")
    public void updateWhitelistEntryRequest() {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);

        Whitelist generatedWhitelist = getRandomWhitelist();
        generatedWhitelist.setId(whitelist.getId());

        context.put("whitelist", generatedWhitelist);
        service.update(generatedWhitelist);
    }

    @When("I send delete whitelist entry request")
    public void deleteWhitelistEntryRequest() {
        Whitelist whitelist = Entities.getWhitelists().getLatest();
        service.remove(whitelist);

        context.put("whitelist", whitelist);
    }

    @When("I send search whitelists by $criteria with $value request")
    public void searchWhitelists(String criteria, String value) {
        Whitelist whitelist = context.get("whitelist", Whitelist.class);

        if (criteria.toLowerCase().equals("identifier")) {
            value = value.equals("random") ? whitelist.getIdentifier() : value;
        } else if (criteria.toLowerCase().equals("description")) {
            value = value.equals("random") ? whitelist.getDescription() : value;
        } else if (criteria.toLowerCase().equals("type")) {
            value = value.equals("random") ? String.valueOf(whitelist.getType()) : value;
        } else if (criteria.toLowerCase().equals("updatedafter")) {
            value = value.equals("random") ? String.valueOf(whitelist.getCreatedAt().getTime() - 60000) : value;
        } else if (criteria.toLowerCase().equals("empty")) {
            log.debug("Search without filter..");
        } else {
            throw new AssertionError("Unknown filter type");
        }

        WhitelistFilter filter = new WhitelistFilter().filterBy(criteria, value);
        OperationResult<List<Whitelist>> operationResult = service.search(filter);

        context.put("whitelistEntitiesList", operationResult.getEntity());
        context.put("whitelistFilter", filter);
    }

    @Then("Whitelists search result is correct")
    @SuppressWarnings("unchecked")
    public void whitelistsSearchResultShouldBeCorrect() {
        log.info("Checking if search targets result is correct");
        WhitelistFilter searchFilter = context.get("whitelistFilter", WhitelistFilter.class);
        List<Whitelist> searchResult = context.get("whitelistEntitiesList", List.class);

        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResult.size());
        }

        String msg = "Whitelist: %s should not match to filter %s";
        for (Whitelist whitelist : searchResult) {
            assertTrue(
                String.format(msg, whitelist, JsonConverter.toJsonString(searchFilter)),
                searchFilter.isAppliedToEntity(whitelist)
            );
        }
    }

    @When("I generate $count random whitelists inside CSV $criteria header")
    public void generateCSVWithWhitelists(final String count, final String criteria) {
        int size = Integer.valueOf(count);
        boolean withHeader = true;
        if (criteria.equalsIgnoreCase("without")) {
            withHeader = false;
        }

        cleanImportDir();
        List<Whitelist> whitelists = getRandomWhitelists(size);
        G4File g4File = service.createCSVFile(whitelists, withHeader);

        context.put("g4file", g4File);
        context.put("importedWhitelists", whitelists);
    }

    @When("I send import whitelists request")
    public void generateCSVWithWhitelists() {
        G4File csvFile = context.get("g4file", G4File.class);
        OperationResult<ImportResult> result = service.upload(csvFile);

        context.put("importResult", result.getEntity());
    }

    @Then("$count whitelists are imported")
    public void numberOfImportedwhitelistsIsCorrect(final String count) {
        Integer size = Integer.valueOf(count);
        ImportResult importResult = context.get("importResult", ImportResult.class);

        assertEquals("Incorrect number of imported whitelists", size, importResult.getImported());
    }

    @Then("I delete imported whitelists")
    @SuppressWarnings("unchecked")
    public void deleteImportedWhitelists() {
        List<Whitelist> whitelists = context.get("importedWhitelists", List.class);
        List<String> errors = new ArrayList<>();
        String error = "Found %d whitelists for identifier %s";

        for (Whitelist importedWhitelist : whitelists) {
            String identifier = importedWhitelist.getIdentifier();
            WhitelistFilter filter = new WhitelistFilter().filterBy("identifier", identifier);

            List<Whitelist> foundWhitelists = service.search(filter).getEntity();
            if (foundWhitelists.size() != 1) {
                errors.add(String.format(error, foundWhitelists.size(), identifier));
            }
            for (Whitelist foundWhitelist : foundWhitelists) {
                service.remove(foundWhitelist);
            }
        }
        if (errors.size() > 0) {
            ErrorReporter.reportAndRaiseError(String.join(".\n", errors));
        }
    }
}
