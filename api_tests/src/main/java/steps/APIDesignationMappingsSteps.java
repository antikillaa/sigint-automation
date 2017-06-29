package steps;

import static ingestion.IngestionService.cleanImportDir;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utils.StringUtils.stringEquals;
import static utils.StringUtils.stripQuotes;

import error_reporter.ErrorReporter;
import http.OperationResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import json.JsonConverter;
import model.Designation;
import model.DesignationMapping;
import model.DesignationMappingFilter;
import model.G4File;
import model.ImportResult;
import model.entities.Entities;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.DesignationMappingService;
import services.DesignationService;
import utils.RandomGenerator;

public class APIDesignationMappingsSteps extends APISteps {

  private static DesignationMappingService service = new DesignationMappingService();
  private static DesignationService designationService = new DesignationService();
  private static List<Designation> designationList;

  private static DesignationMapping getRandomDesignationMapping() {
    return objectInitializer.randomEntity(DesignationMapping.class);
  }

  static List<DesignationMapping> getRandomDesignationMappings(int count) {
    return objectInitializer.randomEntities(DesignationMapping.class, count);
  }

  @When("I send delete designation-mapping request")
  public void deleteDesignationMapping() {
    DesignationMapping designationMapping = Entities.getDesignationMappings().getLatest();
    context.put("designationMapping", designationMapping);
    service.remove(designationMapping);
  }

  @When("I send create new random designation-mapping request")
  public void createRandomDesignationMapping() {
    DesignationMapping designationMapping = getRandomDesignationMapping();

    // init designations list
    if (designationList == null) {
      OperationResult<List<Designation>> operationResult = designationService.list();
      designationList = operationResult.getEntity();
    }
    // Assign random designation to the designation-mapping
    Designation randomDesignation = RandomGenerator.getRandomItemFromList((designationList));
    List<String> designations = Stream.of(randomDesignation.getName()).collect(Collectors.toList());
    designationMapping.setDesignations(designations);

    context.put("designationMapping", designationMapping);
    service.add(designationMapping);
  }

  @When("I send get list of designation-mappings request")
  public void getListOfDesignationMappings() {
    OperationResult<List<DesignationMapping>> operationResult = service.list();
    context.put("designationMappingList", operationResult.getEntity());
  }

  @Then("Designation-mappings list size is more than $size")
  @SuppressWarnings("unchecked")
  public void DesignationMappingsListSizeShouldbeMoreThan(String size) {
    int minSize = Integer.valueOf(size);
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);
    log.info("Designation-mappings list size: " + designationMappings.size());

    assertTrue("List size mismatch. Should be > " + minSize, designationMappings.size() > minSize);
  }

  @Then("Designation-mapping is $criteria list")
  @SuppressWarnings("unchecked")
  public void DesignationMappingShouldBeInList(String criteria) {
    DesignationMapping designationMapping = context.get("designationMapping", DesignationMapping.class);
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);

    boolean contained = false;
    for (DesignationMapping designationMappingFromList : designationMappings) {
      if (designationMappingFromList.getIdentifier().equals(designationMapping.getIdentifier())) {
        contained = true;
        break;
      }
    }

    if (criteria.equals("in")) {
      assertTrue("Designation-mapping " + designationMapping.getId() + " should be in list", contained);
    } else {
      assertFalse("Designation-mapping " + designationMapping.getId() + " should not be listed", contained);
    }
  }

  @When("I get random designation-mapping from list")
  @SuppressWarnings("unchecked")
  public void getRandomDesignationMappingFromList() {
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);
    DesignationMapping randomDesignationMapping = RandomGenerator.getRandomItemFromList((designationMappings));

    context.put("designationMapping", randomDesignationMapping);
  }

  @When("I send view designation-mapping request")
  public void viewDesignationMapping() {
    DesignationMapping designationMapping = context.get("designationMapping", DesignationMapping.class);

    OperationResult<DesignationMapping> operationResult = service.view(designationMapping.getId());
    context.put("designationMapping", operationResult.getEntity());
  }

  @Then("Designation-mapping is correct")
  public void designationMappingShouldBeCorrect() {
    DesignationMapping latestDesignationMapping = Entities.getDesignationMappings().getLatest();
    DesignationMapping designationMapping = context.get("designationMapping", DesignationMapping.class);

    assertTrue("Got null object from collection ",latestDesignationMapping != null);
    assertTrue("Got null object from context ",designationMapping != null);

    assertEquals("identifier mismatch", designationMapping.getIdentifier(), latestDesignationMapping.getIdentifier());
    assertEquals("type mismatch", designationMapping.getType(), latestDesignationMapping.getType());
    assertEquals("designations mismatch", designationMapping.getDesignations(), latestDesignationMapping.getDesignations());
    assertEquals("spam flag mismatch", designationMapping.isSpam(), latestDesignationMapping.isSpam());

    context.put("designationMapping", latestDesignationMapping);
  }

  @When("I send update designation-mapping request")
  public void updateDesignationMapping() {
    DesignationMapping designationMapping = context.get("designationMapping", DesignationMapping.class);

    DesignationMapping generatedDesignationMapping = getRandomDesignationMapping();
    generatedDesignationMapping.setId(designationMapping.getId());

    context.put("designationMapping", generatedDesignationMapping);
    service.update(generatedDesignationMapping);
  }

  @When("I send search designation-mappings by $criteria with $value request")
  public void searchDesignationMappings(String criteria, String value) {
    DesignationMapping designationMapping = context.get("designationMapping", DesignationMapping.class);

    boolean getValueFromEntity = stringEquals("random", value);
    if (stringEquals("identifier", criteria)) {
      value = getValueFromEntity ? designationMapping.getIdentifier() : value;
    } else if (stringEquals("type", criteria)) {
      value = getValueFromEntity ? String.valueOf(designationMapping.getType()) : value;
    } else if (stringEquals("designation", criteria)) {
      value = getValueFromEntity ? String.valueOf(designationMapping.getDesignations().get(0)) : value;
    } else if (stringEquals("updatedAfter", criteria)) {
      value = getValueFromEntity ? String.valueOf(designationMapping.getCreatedAt().getTime() - 60000) : value;
    } else if (stringEquals("empty", criteria)) {
      log.debug("Search without filter..");
    } else {
      throw new AssertionError("Unknown filter type");
    }

    DesignationMappingFilter filter = new DesignationMappingFilter().filterBy(criteria, value);
    OperationResult<List<DesignationMapping>> operationResult = service.search(filter);

    context.put("designationMappingList", operationResult.getEntity());
    context.put("designationMappingFilter", filter);
  }

  @Then("Designation-mappings search result is correct")
  @SuppressWarnings("unchecked")
  public void designationMappingsSearchResultShouldBeCorrect() {
    log.info("Checking if search designation-mappings result is correct");
    DesignationMappingFilter searchFilter = context.get("designationMappingFilter", DesignationMappingFilter.class);
    List<DesignationMapping> searchResult = context.get("designationMappingList", List.class);

    if (searchResult.size() == 0) {
      log.warn("Search result can be incorrect. Found 0 records");
    } else {
      log.info("Search result size: " + searchResult.size());
    }

    String msg = "Designation-mapping: %s should not match filter %s";
    for (DesignationMapping designationMapping : searchResult) {
      assertTrue(
          String.format(msg, designationMapping, JsonConverter.toJsonString(searchFilter)),
          searchFilter.isAppliedToEntity(designationMapping));
    }
  }

  @Given("I generate $count random designation-mappings")
  public void generateDesignationMappingsList(final String count) {
    int size = Integer.valueOf(count);
    List<DesignationMapping> designationMappings = getRandomDesignationMappings(size);

    for (DesignationMapping designationMapping : designationMappings) {
      designationMapping.setDesignations(new ArrayList<>(Collections.singletonList("Import Designation")));
    }

    context.put("designationMappingList", designationMappings);
  }

  @Given("I add the same designation-mapping with $designation designation to the list")
  @SuppressWarnings("unchecked")
  public void addDesignationMappingWithDesignations(final String designation) {
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);

    DesignationMapping newDesignation = designationMappings.get(0).copy();
    newDesignation.setDesignations(new ArrayList<>(Collections.singletonList(stripQuotes(designation))));
    designationMappings.add(newDesignation);

    context.put("designationMappingList", designationMappings);
  }

  @Given("I add broken designation-mapping to the list")
  @SuppressWarnings("unchecked")
  public void addBrokenDesignationMapping() {
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);

    DesignationMapping newDesignation = getRandomDesignationMapping();
    newDesignation.setDesignations(new ArrayList<>(Collections.singletonList("")));
    designationMappings.add(newDesignation);

    context.put("designationMappingList", designationMappings);
  }

  @Given("I write designation-mappings to CSV $criteria header")
  @SuppressWarnings("unchecked")
  public void writeDesignationMappingsToCSV(final String criteria) {
    boolean withHeader = true;
    if (criteria.equalsIgnoreCase("without")) {
      withHeader = false;
    }

    cleanImportDir();
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);
    G4File g4File = service.createCSVFile(designationMappings, withHeader);

    context.put("g4file", g4File);
  }

  @When("I send import designation-mappings request")
  public void importDesignationMappings() {
    G4File csvFile = context.get("g4file", G4File.class);
    OperationResult<ImportResult> result = service.upload(csvFile);

    context.put("importResult", result.getEntity());
  }

  @Then("Imported $countImported designation-mappings, created $countCreated")
  public void numberOfImportedDMIsCorrect(final String countImported, final String countCreated) {
    Integer imported = Integer.valueOf(countImported);
    Integer created = Integer.valueOf(countCreated);
    ImportResult importResult = context.get("importResult", ImportResult.class);

    assertEquals("Incorrect number of imported designation-mappings", imported, importResult.getImported());
    assertEquals("Incorrect number of imported designation-mappings", created, importResult.getCreated());
  }

  @Then("I delete designation-mappings")
  @SuppressWarnings("unchecked")
  public void deleteImportedWhitelists() {
    List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);
    List<String> errors = new ArrayList<>();
    String error = "Found %d designation-mappings for identifier %s";

    for (DesignationMapping importedDesignationMapping : designationMappings) {
      String identifier = importedDesignationMapping.getIdentifier();
      DesignationMappingFilter filter = new DesignationMappingFilter().filterBy("identifier", identifier);

      List<DesignationMapping> foundDesignationMappings = service.search(filter).getEntity();
      if (foundDesignationMappings.size() > 1) {
        errors.add(String.format(error, foundDesignationMappings.size(), identifier));
      }
      for (DesignationMapping foundDesignationMapping : foundDesignationMappings) {
        service.remove(foundDesignationMapping);
      }
    }
    if (errors.size() > 0) {
      ErrorReporter.reportAndRaiseError(String.join(".\n", errors));
    }
  }
}