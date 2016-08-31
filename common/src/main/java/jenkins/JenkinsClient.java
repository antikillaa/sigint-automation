package jenkins;

import app_context.properties.G4Properties;
import app_context.properties.JenkinsProperties;
import json.JsonCoverter;
import json.RsClient;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

class JenkinsClient {
    
    private RsClient client= new RsClient();
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
        Response response = client.get(String.format("%s/job/%s/%s/api/json", jenkinsURL, jobName, jobNumber),
                jenkinsUsername, jenkinsPassword);
        if (response.getStatus()!=200) {
            logger.error("Got error from jenkins when trying to get job status." +
                    "Got code:"+ response.getStatus());
        }
        String json = response.readEntity(String.class);
        JobInfo jobInfo = JsonCoverter.fromJsonToObject(json, JobInfo.class);
        logger.debug("Received job:"+jobInfo);
        return jobInfo;
    }
    
}
