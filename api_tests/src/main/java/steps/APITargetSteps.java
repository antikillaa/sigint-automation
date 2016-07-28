package steps;

import abs.EntityList;
import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.*;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetService;
import utils.Parser;

import java.util.*;

import static conditions.Conditions.equals;
import static conditions.Conditions.isTrue;

public class APITargetSteps extends APISteps {
    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();
    private TargetService service = new TargetService();

    @When("I send create target $with targets group request")
    public void sendCreateRequest(String with) throws NullReturnException {
        Target target = new Target().generate();
        if (with.toLowerCase().equals("with")) {
            List<TargetGroup> groups = new ArrayList<TargetGroup>();
            groups.add(context.entities().getTargetGroups().random());
            target.setGroups(groups);
        }

        int response = service.add(target);

        context.put("code", response);
        context.put("requestTarget", target);
    }

    @Then("Created target is correct")
    public void createdTargetCorrect() {
        Target contextTarget = context.get("requestTarget", Target.class);
        Target createdTarget = context.entities().getTargets().getLatest();

        Assert.assertTrue(createdTarget.getId() != null);
        isEqualsTargets(createdTarget, contextTarget);
    }

    @When("I send get target details request")
    public void getTargetDetails() {
        Target createdTarget = context.entities().getTargets().getLatest();

        Target viewedTarget = service.view(createdTarget.getId());

        context.put("viewedTarget", viewedTarget);
    }

    @Then("Viewed target is correct")
    public void viewedTargetIsCorrect() {
        Target createdTarget = context.entities().getTargets().getLatest();

        Target viewedTarget = context.get("viewedTarget", Target.class);

        Verify.shouldBe(isTrue.element(isEqualsTargets(viewedTarget, createdTarget)));
    }

    private boolean isEqualsTargets(Target checkedTarget, Target etalonTarget) {
        return Verify.isTrue(equals.elements(checkedTarget.getDescription(), etalonTarget.getDescription())) &&
        Verify.isTrue(equals.elements(checkedTarget.getName(), etalonTarget.getName())) &&
        Verify.isTrue(equals.elements(checkedTarget.getKeywords(), etalonTarget.getKeywords())) &&
        Verify.isTrue(equals.elements(checkedTarget.getPhones(), etalonTarget.getPhones())) &&
        Verify.isTrue(equals.elements(checkedTarget.getLanguages(), etalonTarget.getLanguages())) &&
        Verify.isTrue(equals.elements(checkedTarget.getGroups(), etalonTarget.getGroups())) &&
        Verify.isTrue(equals.elements(checkedTarget.getType(), etalonTarget.getType()));
    }

    private boolean isEqualsUploadedTargets(Target checkedTarget, Target etalonTarget) {
        return Verify.isTrue(equals.elements(checkedTarget.getName(), etalonTarget.getName())) &&
                Verify.isTrue(equals.elements(checkedTarget.getKeywords(), etalonTarget.getKeywords())) &&
                Verify.isTrue(equals.elements(checkedTarget.getPhones(), etalonTarget.getPhones())) &&
                Verify.isTrue(equals.elements(checkedTarget.getGroups(), etalonTarget.getGroups())) &&
                Verify.isTrue(equals.elements(checkedTarget.getType(), etalonTarget.getType()));
    }

    @When("I send update target request")
    public void updateTargetRequest() throws NullReturnException {
        Target createdTarget = context.entities().getTargets().getLatest();
        Target updatedTarget = createdTarget.generate();

        log.info("Updated target: " + JsonCoverter.toJsonString(updatedTarget));
        int responseCode = service.update(updatedTarget);

        context.put("code", responseCode);
        context.put("updatedTarget", updatedTarget);
    }

    @Then("Target updated correctly")
    public void targetUpdatedCorrectly() throws NullReturnException {
        Target updatedTarget = context.get("updatedTarget", Target.class);

        Target resultTarget = service.view(updatedTarget.getId());

        Verify.shouldBe(isTrue.element(isEqualsTargets(updatedTarget, resultTarget)));
    }

    @When("I send delete target request")
    public void deleteTargetRequest() {
        Target createdTarget = context.entities().getTargets().getLatest();

        int responseCode = service.remove(createdTarget);

        context.put("deletedTarget", createdTarget);
        context.put("code", responseCode);
    }

    @Then("Target deleted correctly")
    public void targetDeletedCorrectly() throws NullReturnException {
        Target deletedTarget = context.get("deletedTarget", Target.class);

        Target resultTarget = service.view(deletedTarget.getId());

        Verify.shouldBe(isTrue.element(resultTarget.getName().contains("DELETED at")));
    }

    @When("I send upload targets request with XLS file containing $count targets without specified id")
    public void targetGeneratedXls(String count){
        int numTarget = Integer.valueOf(count);

        List<Target> targets = new ArrayList<>();
        for (int i = 0; i < numTarget; i++) {
            targets.add(new Target().generate());
        }

        int responseCode = service.upload(targets);

        context.put("code", responseCode);
        context.put("uploadedTargets", targets);
    }

    @When("I send search targets by $criteria and value $value")
    public void targetSearchByCriteriaAndValue(String criteria, String value) {
        log.info("Start search targets by criteria: " + criteria + ", value: " + value);
        Target target = context.entities().getTargets().getLatest();

        if (criteria.toLowerCase().equals("type")) {
            value = value.equals("random") ? target.getType().toString() : value;
        } else if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? target.getName() : value;
        } else if (criteria.toLowerCase().equals("keywords")) {
            value = value.equals("random") ? Parser.setToString(target.getKeywords()) : value;
        } else if (criteria.toLowerCase().equals("description")) {
            value = value.equals("random") ? target.getDescription() : value;
        } else if (criteria.toLowerCase().equals("deleted")) {
            value = value.equals("random") ? String.valueOf(target.isDeleted()) : value;
        } else if (criteria.toLowerCase().equals("languages")) {
            value = value.equals("random") ? Parser.setToString(target.getLanguages()) : value;
        } else if (criteria.toLowerCase().equals("phones")) {
            value = value.equals("random") ? Parser.setToString(target.getPhones()) : value;
        } else if (criteria.toLowerCase().equals("updatedafter")) {
            value = value.equals("random") ? String.valueOf(target.getCreatedAt().getTime()-1000) : value;
        } else {
            throw new AssertionError("Unknown isAppliedToEntity type");
        }

        TargetFilter searchFilter = new TargetFilter().filterBy(criteria, value);
        EntityList<Target> targets = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", targets);
    }

    @Then("targets search result are correct")
    public void targetSearchResultAreCorrect(){
        log.info("Checking if search targets result is correct");
        TargetFilter searchFilter = context.get("searchFilter", TargetFilter.class);
        EntityList<Target> searchResult = context.get("searchResult", EntityList.class);

        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResult.size());
        }
        for (Target target : searchResult) {
            Assert.assertTrue(String.format("Target:%s should not match to filter %s", target, searchFilter),
                    searchFilter.isAppliedToEntity(target));
        }
    }

    @Then("searched target entry $criteria list")
    public void searchedTargetInList(String criteria) {
        log.info("Checking if Target entry " + criteria + " list");
        Target target = context.entities().getTargets().getLatest();
        EntityList<Target> list = context.get("searchResult", EntityList.class);

        Boolean contains = list.contains(target);
        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @Then("uploaded target $criteria list")
    public void uploadedTargetInList(String criteria) {
        log.info("Checking if Target entry " + criteria + " list");
        Target target = context.entities().getTargets().getLatest();
        EntityList<Target> list = context.get("searchResult", EntityList.class);

        Boolean contains = false;
        for (Target entity : list) {
            if (isEqualsUploadedTargets(target, entity)) {
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

    @Then("target has auto-generated ID")
    public void targetHasID() {
        log.info("Checking if Target has ID");
        EntityList<Target> list = context.get("searchResult", EntityList.class);
        for (Target target : list) {
            Assert.assertNotNull(target.getId());
        }
    }

}
