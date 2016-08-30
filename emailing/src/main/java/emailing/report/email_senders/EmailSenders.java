package emailing.report.email_senders;

import app_context.AppContext;
import org.apache.log4j.Logger;

import java.util.Properties;

public class EmailSenders {
    
    static Properties general = AppContext.getContext().getGeneralProperties();
    private static Logger logger = Logger.getLogger(EmailSenders.class);
    
    
    
    private static Reporters getReporterName() {
        Reporters reporter;
        String propReporter = general.getProperty("emailSender").toUpperCase();
        if (propReporter == null) {
            reporter = Reporters.PEGASUS;
        } else {
            try {
                reporter = Reporters.valueOf(propReporter);
            } catch (IllegalArgumentException e) {
                logger.error("Type of email sender is not recognized as valid:"+ propReporter +
                        "Valid senders types:"+ Reporters.values().toString()+ " .Default value will be used - Pegasus");
                reporter = Reporters.PEGASUS;
            }
        }
        return reporter;
        
    }
    
    public static EmailBaseSender getEmailSender() {
        Reporters reporter = getReporterName();
        if (reporter.equals(Reporters.GMAIL))
            return new GmailSender();
        else
            return  new PegasusSender();
        }
        
    }

