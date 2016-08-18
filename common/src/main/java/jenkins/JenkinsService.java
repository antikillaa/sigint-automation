package jenkins;

import org.apache.log4j.Logger;

public class JenkinsService {
    
    private JenkinsClient jenkinsClient = new JenkinsClient();
    private Logger logger = Logger.getLogger(JenkinsService.class);
    
    
    private JobInfo getJobInfo(String jobNumber) {
        JobInfo jobInfo = jenkinsClient.getJenkinsJobInfo(jobNumber);
        return jobInfo;
    }
    
    private JobStatus getJobStatus(String jobNumber) {
        JobInfo jobInfo = getJobInfo(jobNumber);
        logger.debug("Got job with result:"+jobInfo.getResult());
        return jobInfo.getResult();
    }
    
    public JobStatus getLatestJobStatus() {
        return getJobStatus("lastBuild");
    }
    
    public JobStatus getPreviousJobStatus() {
        String jobInfoIdLatest = getJobInfo("lastBuild").getId();
        String jobInfoIdPrevious = String.valueOf(Integer.valueOf(jobInfoIdLatest)-1);
        return getJobStatus(jobInfoIdPrevious);
    }
}
