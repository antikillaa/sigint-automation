package ae.pegasus.framework.steps.special.search_filter;

import ae.pegasus.framework.constants.special.search_filter.FilterObjectType;
import org.jbehave.core.annotations.Given;
import ae.pegasus.framework.pages.Pages;

import static ae.pegasus.framework.constants.special.search_filter.FilterSetting.OBJECT_TYPE;

public class UISearchFilterObjectTypeSteps {

    private void setObjectType(FilterObjectType objectType) {
        Pages.searchFilterPage().setSingleValueSetting(OBJECT_TYPE, objectType.getObjectName());
    }

    @Given("I set Object Type to All on the Search Filter page")
    public void iSetObjectTypeToAll() {
        setObjectType(FilterObjectType.ALL);
    }

    @Given("I set Object Type to Entity on the Search Filter page")
    public void iSetObjectTypeToEntity() {
        setObjectType(FilterObjectType.ENTITY);
    }

    @Given("I set Object Type to Event on the Search Filter page")
    public void iSetObjectTypeToEvent() {
        setObjectType(FilterObjectType.EVENT);
    }
}
