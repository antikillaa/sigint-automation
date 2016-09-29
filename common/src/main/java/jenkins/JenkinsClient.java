package jenkins;

import app_context.properties.G4Properties;
import app_context.properties.JenkinsProperties;
import http.G4Response;
import http.G4HttpClient;
import http.requests.HttpRequest;
import json.JsonCoverter;
import org.apache.log4j.Logger;

class JenkinsClient {
    
    private G4HttpClient g4HttpClient = new G4HttpClient();
    private String jenkinsURL;
    private String jenkinsUsername;
    private String jenkinsPassword;
    private String jobName;
    private Logger logger = Logger.getLogger(JenkinsClient.class);
    
    JenkinsClient() {
        JenkinsProperties jenkinsConfig  = G4Properties.getJenkinsProperties();
        this.jenkinsURL = jenkinsConfig.getHost();
        this.jenkinsUsername = jenkinsConfig.getUsername();
        this.jenkinsPassword = jenkinsConfig.getPassword();
        this.jobName = jenkinsConfig.getJobName();
    }

    JobInfo getJenkinsJobInfo(String jobNumber) {
        logger.debug("Getting jenkins job info by job number:"+jobNumber);

        String url = String.format("%s/job/%s/%s/api/json", jenkinsURL, jobName, jobNumber);
        HttpRequest request = new HttpRequest(url);
        G4Response response = g4HttpClient.sendRequest(request, jenkinsUsername, jenkinsPassword);

        if (response.getStatus() != 200) {
            logger.error("Got error from jenkins when trying to get job status." +
                    "Got code:" + response.getStatus());
        }

        JobInfo jobInfo = JsonCoverter.readEntityFromResponse(response, JobInfo.class);
        logger.debug("Received job: " + jobInfo);
        return jobInfo;
    }
    
}
