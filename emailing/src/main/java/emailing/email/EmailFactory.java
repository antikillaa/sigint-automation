package emailing.email;

import emailing.email.html_body_builder.FailingContentBuilder;
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
        if (previousJobStatus.equals(JobStatus.FAILURE)) {
            email = (latestJobStatus.equals(JobStatus.FAILURE)) ? new HtmlEmail(new StillFailingContentBuilder()) :
                    new HtmlEmail(new StableContentBuilder());
        }
        else if (previousJobStatus.equals(JobStatus.SUCCESS)) {
            email = (latestJobStatus.equals(JobStatus.FAILURE)) ? new HtmlEmail(new FailingContentBuilder()): null;
        }
        else {
            throw new AssertionError("Email status is not recognized");
        }
        return email;
        
        }
    }

