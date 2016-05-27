package steps;

import abs.SearchFilter;
import model.*;
import abs.EntityList;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RFIService;

import java.text.ParseException;

/**
 * Created by dm on 4/26/16.
 */
public class APIRFISearchSteps extends APISteps {

    private Logger log = Logger.getLogger(APIRFISearchSteps.class);
    private AppContext context = AppContext.getContext();
    private RFIService service = new RFIService();




    @When("I search RFI by $criteria and value $value")
    public void SearchRFIbyCriteria(@Named("criteria") String criteria, @Named("value") String value) throws ParseException {
        log.info("Start search by criteria: "+ criteria);
        InformationRequest RFI = context.entities().getRFIs().getLatest();
        RFISearchFilter searchFilter = new RFISearchFilter();

        if (criteria.toLowerCase().equals("state")) {
            searchFilter.setActiveFilter(searchFilter.new StatusFilter(value.equals("random") ?
                    RFI.getState():value));
        } else if (criteria.toLowerCase().equals("min priority")){
            searchFilter.setActiveFilter(searchFilter.new PriorityFilter(value.equals("random") ?
                    RFI.getPriority(): Integer.parseInt(value)));
        } else if (criteria.toLowerCase().equals("created date")){
            searchFilter.setActiveFilter(searchFilter.new CreatedDateFilter(value.equals("random") ?
                    RFI.getCreatedDate(): RFI.dateFormat.parse(value)));
        } else if (criteria.toLowerCase().equals("due date")) {
            searchFilter.setActiveFilter(searchFilter.new DueDateFilter(value.equals("random") ?
                    RFI.getDueDate(): RFI.dateFormat.parse(value)));
        } else if (criteria.toLowerCase().equals("created by")){
            searchFilter.setActiveFilter(searchFilter.new CreatedByFilter(value.equals("random") ?
                    RFI.getCreatedBy(): value));
        } else if (criteria.toLowerCase().equals("originator")){
            searchFilter.setActiveFilter(searchFilter.new OriginatorFilter(value.equals("random") ?
                    RFI.getRequestSource(): value));
        }
        else {
            throw new AssertionError("Unknown filter type");
        }
        EntityList<InformationRequest> RFIList = service.list(searchFilter);
        context.put("searchFilter", searchFilter);
        context.put("searchResult", RFIList);
    }


    @Then("Search results are correct")
    public void searchResultsCorrect() {
        log.info("Checking if search result is correct");
        SearchFilter filter;
        EntityList <InformationRequest> searchResult;
        filter = context.get("searchFilter", RFISearchFilter.class);
        searchResult = context.get("searchResult", EntityList.class);
        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect." +
                    "There are not records in it");
        }
        for (InformationRequest RFI:searchResult) {
            Assert.assertTrue(filter.filter(RFI));
        }
    }

    @Then("Searched RFI $critera list")
    public void checkRFIinResults(String criteria) {
        InformationRequest request;
        EntityList<InformationRequest> list;
        request = context.get("searchRFI", InformationRequest.class);
        list = context.get("searchResult", EntityList.class);
        Boolean contains = list.contains(request);
        if (criteria.toLowerCase().equals("in")) {
            Assert.assertTrue(contains);
        } else if (criteria.toLowerCase().equals("not in")) {
            Assert.assertFalse(contains);
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }

    }

    @When("I put RFI to search query")
    public void putRFI() {
        InformationRequest RFI = context.entities().getRFIs().getLatest();
        context.put("searchRFI", RFI);

    }

}
