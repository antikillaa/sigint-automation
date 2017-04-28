package steps;

import http.OperationResult;
import model.Tag;
import model.TagFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TagService;

import java.util.List;

public class APITagSteps extends APISteps {

    private Logger log = Logger.getLogger(APITagSteps.class);
    private TagService service = new TagService();

    private static Tag createRandomTag() {
        return objectInitializer.randomEntity(Tag.class);
    }

    @When("I send create new tag request")
    public void createTag() {
        Tag tag = createRandomTag();
        context.put("tag", tag);

        service.add(tag);
    }

    @When("I send get list of tags request")
    public void getListOfTags() {
        OperationResult<List<Tag>> operationResult = service.list();

        context.put("tagList", operationResult.getEntity());
    }

    @Then("Tags list size more than $size")
    public void taglistSizeShouldBeMoreThan(String size) {
        List<Tag> tags = context.get("tagList", List.class);
        Assert.assertTrue(tags.size() > Integer.valueOf(size));
    }

    @When("I send search tags request")
    public void searchTags() {
        TagFilter filter = new TagFilter();
        OperationResult<List<Tag>> operationResult = service.list(filter);

        context.put("tagList", operationResult.getEntity());
    }

    @When("I send delete tag request")
    public void deleteTag() {
        Tag tag = context.get("tag", Tag.class);
        service.remove(tag);
    }

    @When("I get created tag from list of tags")
    public void getRandomtaFromList() {
        List<Tag> tags = context.get("tagList", List.class);
        Tag tag = context.get("tag", Tag.class);

        for (Tag entity : tags) {
            if (entity.getName().equals(tag.getName())) {
                context.put("tag", entity);
                return;
            }
        }
        throw new AssertionError("Tag this name: " + tag.getName() + " not founded in tag list");
    }
}
