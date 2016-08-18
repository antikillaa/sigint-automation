package jenkins;

import org.apache.log4j.Logger;

public class JenkinsService {
    
    private JenkinsClient jenkinsClient = new JenkinsClient();
    private Logger logger = Logger.getLogger(JenkinsService.class);
    
    
    private JobStatus getJobStatus(String jobNumber) {
        JobInfo jobInfo = jenkinsClient.getJenkinsJobInfo(jobNumber);
        logger.error("Got job with result:"+jobInfo.getResult());
        return jobInfo.getResult();
    }
    
    public JobStatus getLatestJobStatus() {
        return getJobStatus("lastBuild");
    }
}
