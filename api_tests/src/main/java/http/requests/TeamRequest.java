package http.requests;

import http.HttpMethod;
import model.Team;

public class TeamRequest extends HttpRequest {

  private static final String URI = "/api/auth/teams/";

  public TeamRequest() {
    super(URI);
  }

  public TeamRequest view(String id) {
    this
        .setURI(URI + id)
        .setHttpMethod(HttpMethod.GET);
    return this;
  }

  public TeamRequest create(Team team) {
    this
        .setURI(URI)
        .setHttpMethod(HttpMethod.POST)
        .setPayload(team);
    return this;
  }

  public TeamRequest update(Team team) {
    this
        .setURI(URI + team.getId())
        .setHttpMethod(HttpMethod.PUT)
        .setPayload(team);
    return this;
  }

  public TeamRequest delete(String id) {
    this
        .setURI(URI + id)
        .setHttpMethod(HttpMethod.DELETE);
    return this;
  }

}
