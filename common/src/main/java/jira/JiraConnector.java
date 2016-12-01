package jira;

import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.HttpRequest;
import jira.model.*;
import http.JsonConverter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Cookie;
import java.util.List;

/**
 * JiraConnector
 */
public class JiraConnector {

    public static Logger log = Logger.getLogger(JiraConnector.class);
    private G4HttpClient client = new G4HttpClient().setHost(G4Properties.getJiraProperties().getJiraServer());
    private Cookie cookie;

    /**
     * JiraConnector instance. Try to connect and initialize cookie.
     */
    public JiraConnector() {
        getCookie();
    }

    /**
     * Return cookie. If cookie is 'null', then try connect to Jira and initialize cookie.
     *
     * @return javax.ws.rs.core Cookie
     */
    private Cookie getCookie() {
        if (cookie == null) {
            Session session = initSession();
            if (session == null) {
                log.debug("jira session wasn't received. Cookie will not be generated");
                return null;
            }
            this.cookie = new Cookie(session.getName(), session.getValue());
        }
        return cookie;
    }

    /**
     * Connect to Jira and get current session info.
     * Need for initialize Jira cookie.
     *
     * @return Session
     */
    private Session initSession() {
        log.debug("Connecting to jira host...");
        JiraSessionRequest request = new JiraSessionRequest();
        G4Response response = client.sendRequest(request);

        SessionInfo sessionInfo = JsonConverter.readEntityFromResponse(response, SessionInfo.class);
        log.debug("Jira session created");
        if (sessionInfo != null) {
            return sessionInfo.getSession();
        } else return null;
    }

    /**
     * Return projectId of project in Jira.
     *
     * @param name Project name
     * @return project id
     * @throws AssertionError if project id is not received.
     */
    public String getProjectId(String name) throws NullReturnException {
        log.debug("Getting project id based on name:" + name);

        String URL = "/rest/api/latest/project";
        HttpRequest request = new HttpRequest(URL).setCookie(cookie);
        G4Response response = client.sendRequest(request);

        List<JiraProject> projects = JsonConverter.readEntitiesFromResponse(response, JiraProject[].class);
        for (JiraProject project : projects) {
            if (project.getName().equals(name)) {
                return project.getId();
            }
        }
        throw new AssertionError("Project id is not received");
    }

    /**
     * Return versionId of project version in Jira.
     *
     * @param projectId   projectId of current Jira project
     * @param versionName version name of current JIra project
     * @return versionId of current Jira project
     * @throws AssertionError There is version with name:versionName for project id:projectId)
     */
    public String getVersionId(String projectId, String versionName) throws NullReturnException {
        log.debug("Getting version id based on project id:" + projectId + " and version name:" + versionName);

        String URL = "/rest/api/latest/project/" + projectId + "/versions";
        HttpRequest request = new HttpRequest(URL).setCookie(cookie);
        G4Response response = client.sendRequest(request);

        List<ProjectVersion> versions = JsonConverter.readEntitiesFromResponse(response, ProjectVersion[].class);

        for (ProjectVersion version : versions) {
            if (version.getName().equals(versionName)) {
                return version.getId();
            }
        }
        throw new AssertionError("There is version with name:" + versionName + " for project id:" + projectId);
    }

    /**
     * Return Jira Issue.
     *
     * @param issueKey issue key, ex: GQ-123, TEEL-1234
     * @return Issue
     */
    public Issue getIssue(String issueKey) {
        log.debug("Getting issue by it's key:" + issueKey);
        String url = "/rest/api/latest/issue/" + issueKey;
        HttpRequest request = new HttpRequest(url).setCookie(cookie);
        G4Response response = client.sendRequest(request);
        return JsonConverter.readEntityFromResponse(response, Issue.class);
    }

}
