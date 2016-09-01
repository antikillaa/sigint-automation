package jira;

import app_context.properties.G4Properties;
import app_context.properties.JiraProperties;
import errors.NullReturnException;
import jira.model.*;
import json.JsonCoverter;
import json.RsClient;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.List;


public class JiraConnector {
    public static Logger log = Logger.getRootLogger();
    private JiraProperties properties = G4Properties.getJiraProperties();

    private RsClient client = new RsClient();
    
    
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
        Response response;
        try {
            response = client.post(properties.getServer() + "/rest/auth/latest/session",
                    JsonCoverter.toJsonString(user));
        } catch (NullReturnException e) {
            log.debug("Cannot convert user to Json!");
            return null;
        }
        SessionInfo sessionInfo = JsonCoverter.fromJsonToObject(
                response.readEntity(String.class), SessionInfo.class);
        log.debug("Jira session created");
        if (sessionInfo !=null) {
            return sessionInfo.getSession();
        }
        else return null;
        

    }

    public String getProjectId(String name) throws NullReturnException {
        log.debug("Getting project id based on name:"+ name);
        String jsonString = client.get(properties.getServer() +
                "/rest/api/latest/project", getCookie()).readEntity(String.class);
        List<JiraProject> projects = JsonCoverter.fromJsonToObjectsList(
                jsonString, JiraProject[].class);
        for (JiraProject project: projects) {
            if (project.getName().equals(name)) {
                return project.getId();
            }
        }
        throw new NullReturnException("Project id is not received");
    }

    public String getVersionId(String projectId, String versionName) throws NullReturnException{
        log.debug("Getting version id based on project id:"+projectId+" and version name:"+versionName);
        String jsonString = client.get(properties.getServer() + "/rest/api/latest/project/"+projectId+"/versions",
                getCookie()).readEntity(String.class);
        List<ProjectVersion> versions = JsonCoverter.fromJsonToObjectsList(jsonString, ProjectVersion[].class);

        for (ProjectVersion version:versions) {
            if (version.getName().equals(versionName)){
                return version.getId();
            }
        }
        throw new NullReturnException("There is version with name:"+versionName+" for project id:"+projectId);
    }
    
    public Issue getIssue(String issueKey) {
        log.debug("Getting issue by it's key:" + issueKey);
        Response response = client.get(properties.getServer() + "/rest/api/latest/issue/"+issueKey,
                getCookie());
        Issue issue = JsonCoverter.readEntityFromResponse(response, Issue.class);
        return issue;
    }

}
