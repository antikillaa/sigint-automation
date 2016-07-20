package email;

import post_build_managers.BuildStatus;

public class EmailFactory {
    
    
    public static HtmlEmail getEmail(BuildStatus status, Boolean isFailed) {
        if (status.equals(BuildStatus.FAILED)) {
            return (isFailed) ? new StillFailingEmail(): new ComeToStableEmail();
        elif (status.equals(BuildStatus.PASSED)) {
            return (isFailed) ? new FailingEmail(): new ShouldNotSendEmail();
            }
    }
}
