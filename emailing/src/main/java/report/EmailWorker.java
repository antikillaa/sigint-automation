package report;

import email.EmailFactory;
import email.HtmlEmail;
import failure_strategy.Statistic;
import model.AppContext;
import org.apache.log4j.Logger;
import post_build_managers.BuildManager;
import reporter.EmailCondition;
import reporter.ReportResults;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailWorker {
    
    private static Properties mailProperties = AppContext.getContext().getMailProperties();
    private static Properties connection = AppContext.getContext().getJiraConnection();
    private static Logger logger = Logger.getLogger(EmailWorker.class);
    private static Integer tries=0;
    
    private static void send(Session session, String html)  {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getProperty("from")));
            message.addRecipients(Message.RecipientType.TO, mailProperties.getProperty("recepients"));
            message.setSubject("Automation build failed");
            message.setContent(html, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            if (tries < 3) {
                tries++;
                send(session, html);
            }
            logger.error("Cannot send email");
            logger.error(e.getMessage());
            
        }
    }
    
    private static void send_email() {
        ReportResults results = Statistic.getResults();
        logger.info("Sending email on failure test results...");
        HtmlEmail email = EmailFactory.getEmail(new BuildManager().getBuildStatus(), Statistic.hasFailuresWithoutBugs() );
        String html = email.buildHtml(results);
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", mailProperties.getProperty("smtp"));
        Session session = Session.getDefaultInstance(prop);
        send(session, html);
    }
    
    public static void main(String[] args) {
        Boolean shouldEmail = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("email"));
        if (shouldEmail) {
            EmailSender(email);
            EmailCondition.setEmailtoSucess();
        }
    }
}
