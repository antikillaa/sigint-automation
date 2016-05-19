package jira;

import errors.NullReturnException;
import jira.model.*;
import model.AppContext;
import org.apache.log4j.Logger;
import json.JsonCoverter;
import json.RsClient;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dm on 4/5/16.
 */
public class JiraConnector {
    public static Logger log = Logger.getRootLogger();
    private AppContext context = AppContext.getContext();
    Cookie sessionCookie;
    private RsClient client = new RsClient();
    private String jiraServer = context.getJiraConnection().getProperty("server");

    public JiraConnector() {
        try {
            Session session = initSession();
            sessionCookie = new Cookie(session.getName(), session.getValue());
        } catch (NullReturnException e) {
            log.warn("Session info wasn't received from Jira. Using anonymous...");
        }

    }


    private Session initSession() throws NullReturnException {
        log.debug("Connecting to jira host...");
        User user = new User();
        String username = context.getJiraConnection().getProperty("username");
        log.debug("Using username:"+username);
        String password = context.getJiraConnection().getProperty("password");
        user.setUsername(username);
        log.debug("Using password:"+password);
        user.setPassword(password);
        Response response = client.post(jiraServer + "/rest/auth/latest/session",
                JsonCoverter.toJsonString(user));
        SessionInfo sessionInfo = JsonCoverter.fromJsonToObject(
                response.readEntity(String.class), SessionInfo.class);
        log.debug("Jira session created");
        return sessionInfo.getSession();

    }

    public String getProjectId(String name) throws NullReturnException {
        log.debug("Getting project id based on name:"+ name);
        String jsonString = client.get(jiraServer +
                "/rest/api/latest/project", sessionCookie).readEntity(String.class);
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
        String jsonString = client.get(jiraServer + "/rest/api/latest/project/"+projectId+"/versions",
                sessionCookie).readEntity(String.class);
        List<ProjectVersion> versions = JsonCoverter.fromJsonToObjectsList(jsonString, ProjectVersion[].class);

        for (ProjectVersion version:versions) {
            if (version.getName().equals(versionName)){
                return version.getId();
            }
        }
        throw new NullReturnException("There is version with name:"+versionName+" for project id:"+projectId);
    }

}
