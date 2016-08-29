package emailing.report.email_senders;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

class GmailSender extends EmailBaseSender{
    
    
    
    
    @Override
    protected Session getSession() {
        Session session = null;
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", mailProperties.getProperty("smtp_gmail"));
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.port", mailProperties.getProperty("port"));
        prop.setProperty("mail.smtp.starttls.enable", "true");
        try {
        session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getProperty("account"),
                        mailProperties.getProperty("password"));
            }
    
        }); } catch (Exception e) {
            logger.error(e.getMessage());
            logger.trace(e.getMessage(), e);
            
        }
        return session;
        }
    }