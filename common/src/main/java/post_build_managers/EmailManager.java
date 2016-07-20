package post_build_managers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailManager extends Manager {
   
    public void updateEmailSendInfo(EmailSendStatus status) throws Exception {
        try {
            initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE info SET status= ? where param=?;");
            preparedStatement.setString(1, status.toString());
            preparedStatement.setString(2, "emailSend");
            preparedStatement.executeUpdate();
        }catch (IOException|SQLException ex) {
            throw ex;
        }
            
    }
    
    public boolean getEmailSendInfo() throws Exception {
        try {
            initConnection();
            ResultSet result = connection.createStatement().executeQuery("SELECT status FROM info WHERE param='emailSend';");
            if (result.next()) {
                return Boolean.parseBoolean(result.getString(1));
            } else {
                throw new SQLException("Cannot get result from SQL Query");
        }
    } catch (IOException|SQLException ex) {
        throw ex;
        }
    }
    
}