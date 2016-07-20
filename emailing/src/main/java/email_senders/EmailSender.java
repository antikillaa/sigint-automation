package email_senders;

import email.HtmlEmail;
import email.ShouldNotSendEmail;

public  abstract class EmailSender {
    
    
    
    public EmailSender getEmailSender(HtmlEmail email) {
        if ( email instanceof ShouldNotSendEmail) {
            return new WillNotSendSender();
        }
    }
    
    
    
    public abstract void send_email(HtmlEmail email);
    
    
    
}
