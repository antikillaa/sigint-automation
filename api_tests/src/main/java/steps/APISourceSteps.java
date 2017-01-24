package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import http.OperationsResults;
import model.RecordType;
import model.Source;
import model.SourceType;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.SourceService;
import utils.Parser;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;


public class APISourceSteps extends APISteps {

    private Logger log = Logger.getLogger(APISourceSteps.class);
    private SourceService service = new SourceService();
    
    @When("I send create new random Source request")
    public void createSource() {
        Source source = getRandomSource();
        OperationResult<Source> operationResult = service.add(source);
        OperationsResults.setResult(operationResult);
        context.put("source", operationResult.getResult());
    }

    @Then("Source is correct")
    public void checkSourceIsCorrect() {
        Source source = context.get("source", Source.class);
        Source latestSource = Entities.getSources().getLatest();

        Verify.shouldBe(Conditions.equals(source, latestSource));
    }

    @When("I send get list of sources request")
    public void getListOfSources() {
       
        EntityList<Source> sources = service.list().getResult();
        context.put("sourceList", sources);
    }

    @Then("Source $criteria list")
    public void sourceShouldBeInList(String criteria){
        Source source = Entities.getSources().getLatest();
        EntityList<Source> sources = context.get("sourceList", EntityList.class);
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
        EntityList<Source> sources = context.get("sourceList", EntityList.class);
        Verify.shouldBe(Conditions.isTrue.element(sources.size() > minSize));
    }

    @When("I send view source request")
    public void viewRandomSourceFromList() {
        Source source = Entities.getSources().getLatest();
        OperationResult<Source> operationResult = service.view(source.getId());
        context.put("source", operationResult.getResult());
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

        OperationResult<Source> operationResult = service.update(generatedSource);
        OperationsResults.setResult(operationResult);
    }

    @Given("Get random source from list")
    public void getRandomSourceFrolList() {
        EntityList<Source> sourceList = context.get("sourceList", EntityList.class);
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
        OperationResult operationResult = service.remove(source);
        OperationsResults.setResult(operationResult);
    }

    @Then("Source is deleted")
    public void sourceShouldBeDeleted() {
        Source source = context.get("source", Source.class);
        Verify.shouldBe(Conditions.isTrue.element(source.isDeleted()));
        Verify.shouldBe(Conditions.isTrue.element(source.getName().contains("DELETED at")));
    }

    @Given("data source with $sourceType and $recordType exists")
    public void getDataSourceWithSourceTypeAndRecordType(String sType, String rType) {
        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);

        // if exist, return source
        EntityList<Source> sources = service.list().getResult();
        for (Source source : sources) {
            if (source.getType().equals(sourceType)) {
                try {
                    RecordType entityRecordType = source.getRecordType();
                    if (entityRecordType.equals(recordType)) {
                        context.put("source", source);
                        return;
                    }
                } catch (NullPointerException e) {
                    log.warn("Source without recordType: " + Parser.entityToString(source));
                }
            }
        }

        // not exist, create source
        Source source = getRandomSource();
        source.setType(sourceType);
        source.setRecordType(recordType);
        source.setName(sourceType.toLetterCode() + "-" + recordType.toEnglishName());
        service.add(source);
        context.put("source", source);
    }

    static Source getRandomSource() {
        return objectInitializer.randomEntity(Source.class);
    }
}
