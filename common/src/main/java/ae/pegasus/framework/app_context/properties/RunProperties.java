package ae.pegasus.framework.app_context.properties;

import java.io.InputStream;

public class RunProperties extends ApplicationProperty {
    
    @Override
    InputStream loadPropertyFile() {
        return this.getClass().getResourceAsStream("/general.properties");
        
    }
    
    public boolean isRemoteRun() {
        return Boolean.parseBoolean(getProperty().getProperty("remoteRun"));
    }
    
    public boolean shouldReport() {
        return Boolean.parseBoolean(getProperty().getProperty("report"));
    }
    
    public boolean shouldEmail() {
        return Boolean.parseBoolean(getProperty().getProperty("email"));
    }
    
    public String getActiveStand() { return getProperty().getProperty("stand");}
    
    public String getApplicationURL() {
        return getProperty().getProperty("sigintURL");
    }
    
    public String getSeleniumHub() {
        return getProperty().getProperty("seleniumHub");
    }
    
    public String getWebBrowser() {
        return getProperty().getProperty("webBrowser");
    }
    
    public String getEmailSender() {return getProperty().getProperty("emailSender");}
}
