package emailing.report;

import emailing.email.EmailFactory;
import utils.FileHelper;

public class EmailFormer {
    
    
    public static void saveHtmlFileToDisk() {
        String htmlEmail = EmailFactory.buildHmtlEmail();
        FileHelper.writeToFile("htmlEmail.html", htmlEmail);
    }
}
