package jira;

import app_context.properties.G4Properties;
import app_context.properties.JiraProperties;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import jira.model.*;
import json.JsonCoverter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Cookie;
import java.util.List;


public class JiraConnector {
    public static Logger log = Logger.getRootLogger();
    private JiraProperties properties = G4Properties.getJiraProperties();

    private G4Client client = new G4Client();


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
        User user = new User();
        String username = properties.getUsername();
        log.debug("Using username:"+username);
        String password = properties.getPassword();
        user.setUsername(username);
        log.debug("Using password:"+password);
        user.setPassword(password);
        G4Response response;
        try {
            response = client.post(properties.getServer() + "/rest/auth/latest/session",
                    JsonCoverter.toJsonString(user));
        } catch (NullReturnException e) {
            log.debug("Cannot convert user to Json!");
            return null;
        }
        SessionInfo sessionInfo = JsonCoverter.readEntityFromResponse(response, SessionInfo.class);
        log.debug("Jira session created");
        if (sessionInfo !=null) {
            return sessionInfo.getSession();
        }
        else return null;


    }

    public String getProjectId(String name) throws NullReturnException {
        log.debug("Getting project id based on name:"+ name);

        G4Response response = client.get(properties.getServer() + "/rest/api/latest/project", getCookie());

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

        String url = properties.getServer() + "/rest/api/latest/project/"+projectId+"/versions";
        G4Response response = client.get(url, getCookie());

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
        String url = properties.getServer() + "/rest/api/latest/issue/" + issueKey;
        G4Response response = client.get(url, getCookie());
        return JsonCoverter.readEntityFromResponse(response, Issue.class);
    }

}
