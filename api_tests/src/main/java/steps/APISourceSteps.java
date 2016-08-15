package steps;

import conditions.Conditions;
import conditions.Verify;
import model.AppContext;
import model.Source;
import model.SourceType;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.SourceService;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.equals;
import static conditions.Conditions.isTrue;

public class APISourceSteps extends APISteps {

    private Logger log = Logger.getLogger(APISourceSteps.class);
    private AppContext context = AppContext.getContext();
    private SourceService service = new SourceService();

    @When("I send create new random Source request")
    public void createSource() {
        Source source = new Source().generate();

        int responseCode = service.add(source);

        context.put("code", responseCode);
        context.put("source", source);
    }

    @Then("Source is correct")
    public void checkSourceIsCorrect() {
        Source source = context.get("source", Source.class);
        Source latestSource = context.entities().getSources().getLatest();

        Verify.shouldBe(Conditions.equals.elements(source, latestSource));
    }

    @When("I send get list of sources request")
    public void getListOfSources() {
        List<Source> sources = service.list();

        context.put("sourceList", sources);
    }

    @Then("Source $criteria list")
    public void sourceShouldBeInList(String criteria){
        Source source = context.entities().getSources().getLatest();
        List<Source> sources = context.get("sourceList", List.class);

        boolean contains = false;
        for (Source entity : sources) {
            if (Conditions.equals.elements(entity, source).check()) {
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

    @Then("Source list size more than $size")
    public void sourseListSizeMoreThan(String size) {
        int minSize = Integer.valueOf(size);
        List<Source> sources = context.get("sourceList", List.class);

        Verify.shouldBe(isTrue.element(sources.size() > minSize));
    }

    @When("I send view source request")
    public void viewRandomSourceFromList() {
        Source source = context.entities().getSources().getLatest();

        Source viewedSource = service.view(source.getId());

        context.put("source", viewedSource);
    }

    @When("I send update source request")
    public void updateRandomSourceFromList() {
        Source source = context.entities().getSources().getLatest();

        // update all fields
        SourceType type = source.getType();
        source = source.generate();
        source.setType(type);

        int responseCode = service.update(source);

        context.put("code", responseCode);
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

        context.entities().getSources().addOrUpdateEntity(source);
    }

    @When("I send delete Source request")
    public void deleteSource() {
        Source source = context.entities().getSources().getLatest();

        int responseCode = service.remove(source);

        context.put("code", responseCode);
    }

    @Then("Source is deleted")
    public void sourceShouldBeDeleted() {
        Source source = context.get("source", Source.class);

        Verify.shouldBe(isTrue.element(source.isDeleted()));
        Verify.shouldBe(isTrue.element(source.getName().contains("DELETED at")));
    }

    @Then("Result message should be '$result'")
    public void resultMessageShouldBe(String result) {
        String resultMessage = context.get("resultMessage", String.class);

        Verify.shouldBe(equals.elements(resultMessage, result));
    }

}
