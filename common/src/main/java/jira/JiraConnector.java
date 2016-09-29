package jira;

import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.HttpRequest;
import jira.model.*;
import json.JsonCoverter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Cookie;
import java.util.List;


public class JiraConnector {

    public static Logger log = Logger.getLogger(JiraConnector.class);
    private G4HttpClient client = new G4HttpClient().setHost(G4Properties.getJiraProperties().getServer());


    private Cookie getCookie() {
        Session session = initSession();
        if (session == null) {
            log.debug("jira session wasn't received. Cookie will not be generated");
            return null;
        }
        return new Cookie(session.getName(), session.getValue());
    }


    private Session initSession()  {
        log.debug("Connecting to jira host...");
        JiraSessionRequest request = new JiraSessionRequest();
        G4Response response = client.sendRequest(request);

        SessionInfo sessionInfo = JsonCoverter.readEntityFromResponse(response, SessionInfo.class);
        log.debug("Jira session created");
        if (sessionInfo != null) {
            return sessionInfo.getSession();
        }
        else return null;
    }

    public String getProjectId(String name) throws NullReturnException {
        log.debug("Getting project id based on name:"+ name);

        String URL = "/rest/api/latest/project";
        HttpRequest request = new HttpRequest(URL);
        G4Response response = client.sendRequest(request);

        List<JiraProject> projects = JsonCoverter.fromJsonToObjectsList(response.getMessage(), JiraProject[].class);
        for (JiraProject project: projects) {
            if (project.getName().equals(name)) {
                return project.getId();
            }
        }
        throw new NullReturnException("Project id is not received");
    }

    public String getVersionId(String projectId, String versionName) throws NullReturnException{
        log.debug("Getting version id based on project id:"+projectId+" and version name:"+versionName);

        String URL = "/rest/api/latest/project/"+projectId+"/versions";
        HttpRequest request = new HttpRequest(URL);
        G4Response response = client.sendRequest(request);

        List<ProjectVersion> versions = JsonCoverter.fromJsonToObjectsList(response.getMessage(), ProjectVersion[].class);

        for (ProjectVersion version:versions) {
            if (version.getName().equals(versionName)){
                return version.getId();
            }
        }
        throw new NullReturnException("There is version with name:"+versionName+" for project id:"+projectId);
    }

    public Issue getIssue(String issueKey) {
        log.debug("Getting issue by it's key:" + issueKey);
        String url = "/rest/api/latest/issue/" + issueKey;
        HttpRequest request = new HttpRequest(url);
        G4Response response = client.sendRequest(request);
        return JsonCoverter.readEntityFromResponse(response, Issue.class);
    }

}
