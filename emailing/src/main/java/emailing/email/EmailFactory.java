package emailing.email;

import emailing.email.html_body_builder.FailingBodyBuilder;
import emailing.email.html_body_builder.StableContentBuilder;
import emailing.email.html_body_builder.StillFailingContentBuilder;
import jenkins.JenkinsService;
import jenkins.JobStatus;

public class EmailFactory {
    
    public static HtmlEmail buildHmtlEmail() {
        
        JenkinsService jenkinsService = new JenkinsService();
        JobStatus latestJobStatus = jenkinsService.getLatestJobStatus();
        JobStatus previousJobStatus = jenkinsService.getPreviousJobStatus();
        HtmlEmail email;
        if (latestJobStatus.equals(JobStatus.FAILURE)) {
            email = (previousJobStatus.equals(JobStatus.FAILURE)) ? new HtmlEmail(new StillFailingContentBuilder()) :
                    new HtmlEmail(new StableContentBuilder());
            
        }
        else if (latestJobStatus.equals(JobStatus.SUCCESS)) {
            email = (previousJobStatus.equals())
        }
        
        
        previousJobStatus.equals(JobStatus.FAILURE)) email =  new HtmlEmail(new FailingBodyBuilder());
        else if (status.equals(JobStatus.SUCCESS)) email =  new HtmlEmail(new StableContentBuilder());
        else throw  new AssertionError("Build status wasn't recognized");
        return  email.getHtmlBody();
        
        }
    
    }

