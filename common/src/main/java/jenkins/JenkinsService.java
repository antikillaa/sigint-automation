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
        if (jobInfo == null) {
            logger.debug("Job info wasn't received");
            return null;
        }
        return jobInfo.getResult();
    }
    
    public JobStatus getLatestJobStatus() {
        return getJobStatus("lastBuild");
    }
    
    public JobStatus getPreviousJobStatus() {
            JobInfo jobInfo = getJobInfo("lastBuild");
            if (jobInfo == null) {
                logger.warn("Cannot get info of job: lastBuild");
                return null;
            }
            String jobInfoIdLatest = jobInfo.getId();
            logger.debug("Latest jenkins job id " + jobInfoIdLatest);
            String jobInfoIdPrevious = String.valueOf(Integer.valueOf(jobInfoIdLatest) - 1);
            logger.debug("Previous jenkins job id " + jobInfoIdPrevious);
        return getJobStatus(jobInfoIdPrevious);
    }
}
