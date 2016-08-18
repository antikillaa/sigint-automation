package emailing.email;

import emailing.email.html_body_builder.StableContentBuilder;
import emailing.email.html_body_builder.StillFailingContentBuilder;
import jenkins.JenkinsService;
import jenkins.JobStatus;

public class EmailFactory {
    
    public static HtmlEmail buildHtmlEmail() {
        
        JenkinsService jenkinsService = new JenkinsService();
        JobStatus latestJobStatus = jenkinsService.getLatestJobStatus();
        JobStatus previousJobStatus = jenkinsService.getPreviousJobStatus();
        HtmlEmail email;
        if (latestJobStatus.equals(JobStatus.FAILURE)) {
            email = (previousJobStatus.equals(JobStatus.FAILURE)) ? new HtmlEmail(new StillFailingContentBuilder()) :
                    new HtmlEmail(new StableContentBuilder());
        }
        else if (latestJobStatus.equals(JobStatus.SUCCESS)) {
            email = (previousJobStatus.equals(JobStatus.FAILURE)) ? new HtmlEmail(new StableContentBuilder()): null;
        }
        else {
            throw new AssertionError("Email status is not recognized");
        }
        return email;
        
        }
    }

