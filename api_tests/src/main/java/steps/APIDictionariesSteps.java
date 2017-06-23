package steps;

import http.OperationResult;
import model.RecordType;
import model.SourceType;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.DictionaryService;

import java.util.List;

@SuppressWarnings("unchecked")
public class APIDictionariesSteps extends APISteps {

    @When("I send get list of dictionary subSources request")
    public void getListOfsubSources() {
        OperationResult<List<RecordType>> operationResult = DictionaryService.getSubSources();

        context.put("dictionarySubSources", operationResult.getEntity());
    }

    @Then("SubSources list size is more than $size")
    public void subSourcesListMoreThan(String size) {
        List<RecordType> subSources = context.get("dictionarySubSources", List.class);

        Assert.assertTrue(subSources.size() > Integer.valueOf(size));
    }

    @When("I send get list of dictionary sources request")
    public void getAllSources() {
        OperationResult<List<SourceType>> operationResult = DictionaryService.getSources();

        context.put("dictionarySources", operationResult.getEntity());
    }

    @Then("Dictionary source list size more than $size")
    public void dictionarySourcesListMoreThan(String size) {
        List<SourceType> sources = context.get("dictionarySources", List.class);

        Assert.assertTrue(sources.size() > Integer.valueOf(size));
    }
}
