package steps;

import http.OperationResult;
import model.SourceType;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.DictionaryService;

import java.util.List;

@SuppressWarnings("unchecked")
public class APIDictionariesSteps extends APISteps {

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
