package steps;

import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import model.Team;
import model.entities.Entities;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TeamService;

import java.util.List;

@SuppressWarnings("unchecked")
public class APITeamManagementSteps extends APISteps {

    private static TeamService teamService = new TeamService();

    private Team createRandomTeam() {
        return objectInitializer.randomEntity(Team.class);
    }

    @When("I send create a new team")
    public void createNewTeam() {
        Team team = createRandomTeam();
        context.put("team", team);

        teamService.add(team);
    }

    @Then("Team is correct")
    public void createdTeamShouldBeCorrect() {
        Team team = context.get("team", Team.class);
        Team created = Entities.getTeams().getLatest();

        Assert.assertEquals(created.getDescription(), team.getDescription());
        Verify.shouldBe(Conditions.equals(created.getDefaultPermission(), team.getDefaultPermission()));
        Assert.assertEquals(created.getFullName(), team.getFullName());
        Assert.assertEquals(created.getOrganizationType(), team.getOrganizationType());
        Assert.assertEquals(created.getParentTeamId(), team.getParentTeamId());
    }

    @When("I send delete team request")
    public void deleteTeam() {
        Team team = Entities.getTeams().getLatest();
        teamService.remove(team);
    }

    @When("I send get list of teams")
    public void getListOfTeams() {
        OperationResult<List<Team>> operationResult = teamService.list();
        context.put("teamList", operationResult.getEntity());
    }

    @Then("Teams list size more than $size")
    public void teamListSizeShouldBeMoreThan(String size) {
        List<Team> teams = context.get("teamList", List.class);

        Assert.assertTrue(teams.size() > Integer.valueOf(size));
    }

    @Then("Team is $criteria the list")
    public void checkTeamInList(String criteria) {
        List<Team> teams = context.get("teamList", List.class);
        Team Team = context.get("team", Team.class);

        boolean matched = teams.stream()
                .anyMatch((r -> r.getId().equals(Team.getId())));

        if (criteria.equals("in")) {
            Assert.assertTrue(matched);
        } else {
            Assert.assertFalse(matched);
        }
    }

    @When("I send update team request")
    public void updateTeam() {
        Team team = Entities.getTeams().getLatest();

        Team updatedTeam = createRandomTeam();
        updatedTeam.setId(team.getId());
        context.put("team", updatedTeam);

        teamService.update(updatedTeam);
    }

    @When("I send get team details request")
    public void getTeamDetails() {
        Team team = Entities.getTeams().getLatest();
        context.put("team", team);

        teamService.view(team.getId());
    }
}
