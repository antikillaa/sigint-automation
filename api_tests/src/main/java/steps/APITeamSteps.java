package steps;

import model.Team;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.When;
import services.TeamService;

@SuppressWarnings("unchecked")
public class APITeamSteps extends APISteps {

  private static final Logger log = Logger.getLogger(APITeamSteps.class);
  private TeamService teamService = new TeamService();

  @When("I create new team")
  public void createTeam() {
    Team team = objectInitializer.randomEntity(Team.class);
    teamService.add(team);
    context.put("team", team);
  }
}
