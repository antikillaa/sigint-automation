package jenkins;

import http.G4Response;
import http.client.G4Client;
import json.JsonCoverter;
import model.AppContext;
import org.apache.log4j.Logger;

import java.util.Properties;

class JenkinsClient {
    
    private G4Client client = new G4Client();
    private String jenkinsURL;
    private String jenkinsUsername;
    private String jenkinsPassword;
    private String jobName;
    private Logger logger = Logger.getLogger(JenkinsClient.class);
    
    JenkinsClient() {
        Properties jenkinsConfig  = AppContext.getContext().getJenkinsProperties();
        this.jenkinsURL = jenkinsConfig.getProperty("jenkinsHost");
        this.jenkinsUsername = jenkinsConfig.getProperty("username");
        this.jenkinsPassword = jenkinsConfig.getProperty("password");
        this.jobName = jenkinsConfig.getProperty("jobName");
    }
    
    JobInfo getJenkinsJobInfo(String jobNumber) {
        logger.debug("Getting jenkins job info by job number:"+jobNumber);

        String url = String.format("%s/job/%s/%s/api/json", jenkinsURL, jobName, jobNumber);
        G4Response response = client.get(url, jenkinsUsername, jenkinsPassword);

        if (response.getStatus() != 200) {
            logger.error("Got error from jenkins when trying to get job status." +
                    "Got code:" + response.getStatus());
        }

        JobInfo jobInfo = JsonCoverter.readEntityFromResponse(response, JobInfo.class);
        logger.debug("Received job: " + jobInfo);
        return jobInfo;
    }
    
}
