package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.Organization;
import ae.pegasus.framework.model.OrganizationFilter;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.OrganizationService;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.List;

@SuppressWarnings("unchecked")
public class APIOrganizationSteps extends APISteps {

    private static OrganizationService organizationService = new OrganizationService();


    @When("I send search users and teams by $criteria with $value request")
    public void searchUsersAndTeamsByCriteria(String criteria, String value) {
        Organization organization = Entities.getOrganizations().getLatest();

        if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? organization.getFullName() : value;
        } else if (criteria.toLowerCase().equals("orgtypes")) {
            value = value.equals("random") ? organization.getOrganizationType().name() : value;
        } else if (criteria.toLowerCase().equals("datasources")) {
            value = value.equals("random") ? organization.getDefaultPermission().getRecord().getDataSources().toString() : value;
        } else if (criteria.toLowerCase().equals("clearances")) {
            value = value.equals("random") ? organization.getDefaultPermission().getRecord().getClearances().toString() : value;
        } else if (criteria.toLowerCase().equals("titles")) {
            value = value.equals("random") ? organization.getDefaultPermission().getTitles().toString() : value;
        } else if (criteria.toLowerCase().equals("orgids")) {
            value = value.equals("random") ? organization.getId() : value;
        } else if (criteria.toLowerCase().equals("parentorgid")) {
            value = value.equals("random") ? organization.getParentTeamId() : value;
        } else {
            throw new AssertionError("Unknown filter field for organization search: " + criteria);
        }

        OrganizationFilter filter = new OrganizationFilter().filterBy(criteria, value);
        OperationResult<List<Organization>> operationResult = organizationService.search(filter);

        context.put("organizationList", operationResult.getEntity());
        context.put("organizationFilter", filter);
    }

    @Then("Users and Teams search result are correct")
    public void searchResultAreCorrect() {
        List<Organization> organizations = context.get("organizationList", List.class);
        OrganizationFilter organizationFilter = context.get("organizationFilter", OrganizationFilter.class);

        Long count = organizations.stream()
                .peek(organization -> Assert.assertTrue(
                        "Entity does not applied to filter!" +
                                "\n Entity:" + JsonConverter.toJsonString(organization) +
                                "\n Filter:" + JsonConverter.toJsonString(organizationFilter),
                        organizationFilter.isAppliedToEntity(organization)))
                .count();
    }

    @Then("Users and Teams list size more than $size")
    public void teamListSizeShouldBeMoreThan(String size) {
        List<Organization> organizations = context.get("organizationList", List.class);

        Assert.assertTrue(organizations.size() > Integer.valueOf(size));
    }

    @Then("Users and Teams list contains created user or team")
    public void usersAndTeamsListContainCreatedTeam() {
        List<Organization> organizations = context.get("organizationList", List.class);
        Organization organization = Entities.getOrganizations().getLatest();

        Assert.assertTrue(organizations.stream().anyMatch(org -> org.getId().equals(organization.getId())));
    }
}
