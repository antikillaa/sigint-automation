package emailing.report;

import model.AppContext;

public class EmailWorker {
    
    public static void main(String[] args) {
        Boolean shouldEmail = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("email"));
        if (shouldEmail) EmailFormer.saveHtmlFileToDisk();
    }
}
