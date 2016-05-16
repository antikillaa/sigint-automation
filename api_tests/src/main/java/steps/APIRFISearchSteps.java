package steps;

import errors.NullReturnException;
import model.AppContext;
import model.InformationRequest;
import model.RFISearchFilter;
import model.SearchFilter;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dm on 4/26/16.
 */
public class APIRFISearchSteps {

    private Logger log = Logger.getRootLogger();
    private AppContext context = AppContext.getContext();


    @When("I search RFI by $criteria")
    public void SearchRFIbyCriteria(@Named("criteria") String criteria) {
        log.info("Start search by criteria: "+ criteria);
        InformationRequest RFI;
        try {
            RFI = context.getEntitiesList(RFIList.class).getLatest();
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("RFI wasn't recieved from collection");
        }
        RFISearchFilter searchFilter = new RFISearchFilter();

        if (criteria.toLowerCase().equals("state")) {
            searchFilter.setActiveFilter(searchFilter.new StatusFilter(RFI.getState()));
        } else if (criteria.toLowerCase().equals("min priority")){
            searchFilter.setActiveFilter(searchFilter.new PriorityFilter(RFI.getPriority()));
        } else if (criteria.toLowerCase().equals("created date")){
            searchFilter.setActiveFilter(searchFilter.new CreatedDateFilter(RFI.getCreatedDate()));
        } else if (criteria.toLowerCase().equals("due date")) {
            searchFilter.setActiveFilter(searchFilter.new DueDateFilter(RFI.getDueDate()));
        } else if (criteria.toLowerCase().equals("created by")){
            searchFilter.setActiveFilter(searchFilter.new CreatedByFilter(RFI.getCreatedBy()));
        } else if (criteria.toLowerCase().equals("originator")){
            searchFilter.setActiveFilter(searchFilter.new OriginatorFilter(RFI.getRequestSource()));
        }
        else {
            throw new AssertionError("Unknown filter type");
        }
        List<InformationRequest> RFIList = APIRFIUploadSteps.service.list(searchFilter);
        context.putToRunContext("searchFilter", searchFilter);
        context.putToRunContext("searchResult", RFIList);
    }


    @Then("Search results are correct")
    public void searchResultsCorrect() {
        log.info("Checking if search result is correct");
        SearchFilter filter;
        List <InformationRequest> searchResult;
        try {
            filter = context.getFromRunContext("searchFilter", RFISearchFilter.class);
            searchResult = context.getFromRunContext("searchResult", ArrayList.class);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Cannot get search results");
        }
        if (searchResult.size() == 0) {
            throw new AssertionError("Search result is incorrect. At least 1 record " +
                    "should be found");
        }
        for (InformationRequest RFI:searchResult) {
            Assert.assertTrue(filter.filter(RFI));
        }
    }

}
