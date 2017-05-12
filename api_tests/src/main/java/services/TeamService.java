package services;

import model.SearchFilter;
import model.entities.Entities;
import http.G4Response;
import http.OperationResult;
import http.requests.TeamRequest;
import model.Result;
import model.Team;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

public class TeamService implements EntityService<Team> {

  private static final Logger log = Logger.getLogger(TeamService.class);
  private static final TeamRequest request = new TeamRequest();

  @Override
  public OperationResult<Team> view(String id) {
    log.info("Getting Team details by id: " + id);

    G4Response response = g4HttpClient.sendRequest(request.view(id));

    OperationResult<Team> operationResult = new OperationResult<>(response, Team.class);
    if (operationResult.isSuccess()) {
      Entities.getTeams().addOrUpdateEntity(operationResult.getEntity());
    }
    return operationResult;
  }

  @Override
  public OperationResult<Team> add(Team entity) {
    log.info("Team creating");

    G4Response response = g4HttpClient.sendRequest(request.create(entity));

    OperationResult<Team> operationResult = new OperationResult<>(response, Team.class);
    if (operationResult.isSuccess()) {
      Team team = operationResult.getEntity();
      log.info("New Team id: " + team.getId() + " and fullname: " + team.getFullName());
      Entities.getTeams().addOrUpdateEntity(team);
    }
    return operationResult;
  }

  @Override
  public OperationResult<Team> update(Team entity) {
    log.info("Update Team with id: " + entity.getId());

    G4Response response = g4HttpClient.sendRequest(request.update(entity));

    OperationResult<Team> operationResult = new OperationResult<>(response, Team.class);
    if (operationResult.isSuccess()) {
      Entities.getTeams().addOrUpdateEntity(operationResult.getEntity());
    }
    return operationResult;
  }

  @Override
  public OperationResult<Result> remove(Team entity) {
    log.info("Deleting Team with id:" + entity.getId() + " and fullname: " + entity.getFullName());

    G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

    OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
    if (operationResult.isSuccess()) {
      Entities.getTeams().removeEntity(entity);
    }
    return operationResult;
  }

  @Override
  public OperationResult<List<Team>> search(SearchFilter filter) {
    throw new NotImplementedException();
  }

  @Override
  public OperationResult<List<Team>> list() {
    throw new NotImplementedException();
  }

}
