package jenkins;

public class JenkinsService {
    
    private JenkinsClient jenkinsClient = new JenkinsClient();

    
    
    private JobStatus getJobStatus(String jobNumber) {
        JobInfo jobInfo = jenkinsClient.getJenkinsJobInfo(jobNumber);
        return jobInfo.getResult();
    }
    
    public JobStatus getLatestJobStatus() {
        return getJobStatus("lastBuild");
    }
}
