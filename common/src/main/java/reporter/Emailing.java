package reporter;

import html_email.HtmlEmail;
import model.AppContext;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emailing {

    private Properties mailProperties = AppContext.getContext().getMailProperties();
    private Logger logger = Logger.getLogger(Emailing.class);
    private Integer tries=0;


    public void send_email(ReportResults results) {
        HtmlEmail email = new HtmlEmail();
        String html = email.buildHtml(results);
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", mailProperties.getProperty("smtp"));
        Session session = Session.getDefaultInstance(prop);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getProperty("from")));
            message.addRecipients(Message.RecipientType.TO, mailProperties.getProperty("recepients"));
            message.setSubject("Automation build failed");
            message.setContent(html, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            if (tries < 4) {
                tries++;
                send_email(results);
            }
                logger.error("Cannot send email");
                logger.error(e.getMessage());

            }

        }

    }

