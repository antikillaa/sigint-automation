package post_build_managers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildManager extends Manager {
    
    private Logger logger = Logger.getLogger(BuildManager.class);
    
    public  void updateBuildStatus(Boolean isFailed) {
        try {
            initConnection();
            BuildStatus status = (isFailed) ? BuildStatus.FAILED : BuildStatus.PASSED;
            updateBuild(status.toString());
        } catch (IOException|SQLException ex) {
            logger.error("Cannot update build status due to DB error");
            logger.error(ex.getMessage());
        }
    }
    
    public BuildStatus getBuildStatus()  {
            try {
                initConnection();
                ResultSet result = connection.createStatement().executeQuery("SELECT status FROM info WHERE param='buildResult';");
                if (result.next()) {
                    return BuildStatus.valueOf(result.getString("buildResult"));
                } else {
                    throw new SQLException("Cannot get result from SQL Query");
                }
            } catch (IOException| SQLException ex) {
                logger.error("Error occurred getting status of build status. UNKNOWN will be used");
                logger.error(ex.getMessage());
                return BuildStatus.UNKNOWN;
            }
        
    }
    
    private void updateBuild(String status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE info SET status=? where param=buildResult;");
        preparedStatement.setString(1, status);
        preparedStatement.executeUpdate();
    }
    
}
