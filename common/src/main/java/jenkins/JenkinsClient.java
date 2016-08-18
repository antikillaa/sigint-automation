package jenkins;

import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.Properties;

class JenkinsClient {
    
    private RsClient client= new RsClient();
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
        logger.info("Getting jenkins job info by job number:"+jobNumber);
        Response response = client.get(String.format("%s/job/%s/%s/api/json", jenkinsURL, jobName, jobNumber),
                jenkinsUsername, jenkinsPassword);
        if (response.getStatus()!=200) {
            logger.error("Got error from jenkins when trying to get job status." +
                    "Got code:"+ response.getStatus());
        }
        JobInfo jobInfo = JsonCoverter.fromJsonToObject(response.readEntity(String.class), JobInfo.class);
        logger.info("Received job:"+jobInfo);
        return jobInfo;
    }
    
}
