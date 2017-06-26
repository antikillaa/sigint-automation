package steps;

import app_context.AppContext;
import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import json.JsonConverter;
import model.RecordType;
import model.Source;
import model.SourceType;
import model.entities.Entities;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.SourceService;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class APISourceSteps extends APISteps {

    private Logger log = Logger.getLogger(APISourceSteps.class);
    private SourceService service = new SourceService();

    @When("I send create new random Source request")
    public void createSource() {
        Source source = getRandomSource();
        OperationResult<Source> operationResult = service.add(source);
        context.put("source", operationResult.getEntity());
    }

    @Then("Source is correct")
    public void checkSourceIsCorrect() {
        Source source = context.get("source", Source.class);
        Source latestSource = Entities.getSources().getLatest();

        Verify.shouldBe(Conditions.equals(source, latestSource));
    }

    @When("I send get list of sources request")
    public void getListOfSources() {
        List<Source> sources = service.list().getEntity();
        context.put("sourceList", sources);
    }

    @Then("Source $criteria list")
    public void sourceShouldBeInList(String criteria) {
        Source source = context.get("source", Source.class);
        List<Source> sources = context.get("sourceList", List.class);
        boolean contains = false;
        for (Source entity : sources) {
            if (entity.getName().equals(source.getName())) {
                contains = true;
                break;
            }
        }
        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(Conditions.isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(Conditions.isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @Then("Source list size more than $size")
    public void sourseListSizeMoreThan(String size) {
        int minSize = Integer.valueOf(size);
        List<Source> sources = context.get("sourceList", List.class);
        Verify.shouldBe(Conditions.isTrue.element(sources.size() > minSize));
    }

    @When("I send view source request")
    public void viewRandomSourceFromList() {
        Source source = Entities.getSources().getLatest();
        OperationResult<Source> operationResult = service.view(source.getId());
        context.put("source", operationResult.getEntity());
    }

    @When("I send update source request")
    public void updateRandomSourceFromList() {
        Source generatedSource = Entities.getSources().getLatest();

        // update all fields
        Source newSource = getRandomSource();
        generatedSource.setName(newSource.getName());
        generatedSource.setLocation(newSource.getLocation());
        generatedSource.setRecordType(newSource.getRecordType());
        generatedSource.setLatitude(newSource.getLatitude());
        generatedSource.setLongitude(newSource.getLongitude());
        context.put("source", generatedSource);

        service.update(generatedSource);
    }

    @Given("Get random source from list")
    public void getRandomSourceFrolList() {
        List<Source> sourceList = context.get("sourceList", List.class);
        List<Source> sources = new ArrayList<>();
        for (Source source : sourceList) {
            if (!source.isDeleted()) {
                sources.add(source);
            }
        }
        Source source = RandomGenerator.getRandomItemFromList(sources);
        Entities.getSources().addOrUpdateEntity(source);
    }

    @When("I send delete Source request")
    public void deleteSource() {
        Source source = Entities.getSources().getLatest();
        service.remove(source);
        context.put("source", source);
    }

    @Then("Source is deleted")
    public void sourceShouldBeDeleted() {
        Source source = context.get("source", Source.class);
        Verify.shouldBe(Conditions.isTrue.element(source.isDeleted()));
        Verify.shouldBe(Conditions.isTrue.element(source.getName().contains("DELETED at")));
    }

    @Given("Data source with $sourceType and $recordType exists")
    public void getDataSourceWithSourceTypeAndRecordType(String sType, String rType) {
        SourceType sourceType = appContext.getDictionary().getSourceType(sType);
        RecordType recordType = appContext.getDictionary().getRecordType(rType);

        // if exist, return source
        List<Source> sources = service.list().getEntity();
        for (Source source : sources) {
            if (Objects.equals(source.getType(), sourceType.getType())) {
                try {
                    if (Objects.equals(source.getRecordType(), recordType.getType())) {
                        log.info(source.getName() + " source will be used");
                        context.put("source", source);
                        return;
                    }
                } catch (NullPointerException e) {
                    log.warn("Source without recordType: " + JsonConverter.toJsonString(source));
                }
            }
        }

        // not exist, create source
        Source source = getRandomSource();
        source.setType(sourceType.getType());
        source.setRecordType(recordType.getType());
        source.setName(sourceType.getType() + "-" + recordType.getType());
        service.add(source);
        context.put("source", source);
    }

    static Source getRandomSource() {
        return objectInitializer.randomEntity(Source.class);
    }

    /**
     * cleanup data on env
     * TODO remove after moved to CB
     */
    @Then("delete all old sources")
    public void deleteOldSources() {
        List<Source> sourceList = context.get("sourceList", List.class);
        List<SourceType> sourceTypes = AppContext.get().getDictionary().getSourceTypes();
        List<RecordType> recordTypes = AppContext.get().getDictionary().getRecordTypes();

        for (Source source : sourceList) {
            if (!source.isDeleted()) {
                String[] strings = source.getName().split("-");
                if (strings.length == 3) {
                    for (SourceType sourceType : sourceTypes) {
                        if (sourceType.getLetterCode().equals(strings[0])) {
                            for (RecordType recordType : recordTypes) {
                                if (recordType.getEnglishName().equals(strings[1])) {
                                    if (strings[2].length() == 10) {
                                        OperationResult operationResult = service.remove(source);
                                        if (!operationResult.isSuccess())
                                            throw new Error("Unable to remove source: " + source.getName());
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
