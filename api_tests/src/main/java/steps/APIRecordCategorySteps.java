package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import data_generator.DataGenerator;
import model.RecordCategory;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RecordCategoryService;
import utils.Parser;
import utils.RandomGenerator;

import java.util.List;

@SuppressWarnings("unused")
public class APIRecordCategorySteps extends APISteps {

    private Logger logger = Logger.getLogger(APIReportSteps.class);
    private RecordCategoryService service = new RecordCategoryService();

    @When("I send create new random record category request")
    public void createRandomRecordCategory() {
        DataGenerator dataGenerator = new DataGenerator(RecordCategory.class);
        RecordCategory recordCategory = (RecordCategory) dataGenerator.produce();

        int responseCode = service.add(recordCategory);

        context.put("code", responseCode);
        context.put("requestRecordCategory", recordCategory);
    }
    
    
    @When("I send get list of record categories request")
    public void getListOfRecordCategory() {
        List<RecordCategory> categories = service.list();
        context.put("recordCategories", categories);
    }

    @Then("Record categories list size more than $size")
    @SuppressWarnings("unchecked")
    public void recordCategoriesListSizeShouldbeMoroThan(String size) {
        int lessSize = Integer.valueOf(size);
        List<RecordCategory> categories = context.get("recordCategories", List.class);
        Verify.shouldBe(Conditions.isTrue.element(categories.size() > lessSize));
    }

    @Then("Record category is $criteria list")
    @SuppressWarnings("unchecked")
    public void recordcateroryShouldBeInList(String criteria) {
        RecordCategory category = context.get("requestRecordCategory", RecordCategory.class);
        List<RecordCategory> categories = context.get("recordCategories", List.class);

        boolean contained = false;
        for (RecordCategory recordCategory : categories) {
            if (recordCategory.equals(category)) {
                contained = true;
                break;
            }
        }

        if (criteria.equals("in")) {
            Verify.shouldBe(Conditions.isTrue.element(contained));
        } else {
            Verify.shouldNotBe(Conditions.isTrue.element(contained));
        }
    }

    @When("I get random record category from list")
    @SuppressWarnings("unchecked")
    public void getRandomRecordCategoryfromList() {
        List<RecordCategory> categories = context.get("recordCategories", List.class);
        RecordCategory recordCategory = RandomGenerator.getRandomItemFromList(categories);

        Verify.shouldBe(Conditions.isTrue.element(recordCategory != null));
        context.put("recordCategory", recordCategory);
    }

    @When("I send view record category request")
    public void viewRecordCategory() {
        RecordCategory category = context.get("recordCategory", RecordCategory.class);

        RecordCategory recordCategory = service.view(category.getId());

        Verify.shouldBe(Conditions.isTrue.element(recordCategory != null));
        context.put("requestRecordCategory", recordCategory);
    }

    @Then("Record category is correct")
    public void recordCategoryShouldBeCorrect() {
        RecordCategory category = Entities.getRecordCategories().getLatest();
        RecordCategory recordCategory = context.get("requestRecordCategory", RecordCategory.class);

        Verify.shouldBe(Conditions.equals(category, recordCategory));
    }

    @When("I send update record category request")
    public void updateRecordCategory() {
        RecordCategory category = context.get("recordCategory", RecordCategory.class);
        logger.debug("Update record-category: " + Parser.entityToString(category));

        DataGenerator dataGenerator = new DataGenerator(RecordCategory.class);
        RecordCategory recordCategory = (RecordCategory) dataGenerator.produce();
        recordCategory.setId(category.getId());

        int status = service.update(recordCategory);
        Verify.shouldBe(Conditions.isTrue.element(status == 200));

        context.put("requestRecordCategory", recordCategory);
    }
}
