package emailing.report;

import emailing.email.EmailFactory;
import emailing.email.HtmlEmail;
import emailing.report.email_senders.EmailSenders;
import model.AppContext;
import org.apache.log4j.Logger;

public class EmailWorker {
    
    private static Logger logger = Logger.getLogger(EmailWorker.class);
    
    public static void main(String[] args) {
        Boolean shouldEmail = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("email"));
        if (shouldEmail) {
            HtmlEmail htmlEmail;
            try {
                logger.info("Forming email based on the current build results");
                htmlEmail = EmailFactory.buildHtmlEmail();
                if (htmlEmail == null) {
                    logger.info("Email will not be send as it is currently stable");
                    return;
                }
            } catch (AssertionError e) {
                logger.warn("Email will not be sent due to the error occurred while forming email");
                logger.debug(e.getMessage());
                logger.trace(e.getMessage(), e);
                return;
            }
            EmailSenders.getEmailSender().send_email(htmlEmail.getHtmlBody(), htmlEmail.getSubject());
            logger.info("Html Email is sent");
        }
    }
}
