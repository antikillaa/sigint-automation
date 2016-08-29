package app_context.properties;

import java.io.InputStream;

public class MailProperties extends ApplicationProperty {
    
    MailProperties(){}
    
    @Override
    InputStream loadPropertyFile() {
        return getClass().getClassLoader().getResourceAsStream("email.properties");
    }
    
    
    public String getRecipients() {
        return getProperty().getProperty("recipients");
    }
    
    public String getFromAddress() {
        return getProperty().getProperty("from");
    }
    
    public String getStmpHost() {
        return getProperty().getProperty("smtp");
    }
    
    public String getPort() {
        return getProperty().getProperty("port");
    }
    
    public String getAccount() {
        return getProperty().getProperty("account");
    }
    
    public String getPassword() {
        return getProperty().getProperty("password");
    }
    
}
