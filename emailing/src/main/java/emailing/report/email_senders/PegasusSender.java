package emailing.report.email_senders;

import javax.mail.Session;
import java.util.Properties;

class PegasusSender extends EmailBaseSender {
    @Override
    protected Session getSession() {
        Session session = null;
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", mailProperties.getProperty("smtp_pegasus"));
        try {
            session = Session.getDefaultInstance(prop);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.trace(e.getMessage(), e);
        }
        return session;
    }
}
