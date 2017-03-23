package app_context.properties;

import app_context.Stand;
import error_reporter.ErrorReporter;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class MongoConnectionProperties {

    private static Logger log = Logger.getLogger(MongoConnectionProperties.class);

    public static String getHost() {
        Stand stand = Stand.DEVELOP;

        String standString = G4Properties.getRunProperties().getActiveStand();
        try {
            stand = Stand.valueOf(standString.toUpperCase());
        } catch (IllegalArgumentException e) {
            ErrorReporter.reportAndRaiseError(String.format("Unknown stand is used: %s!." +
                    "Should be between %s", standString, Arrays.toString(Stand.values())));
        }
        String mongoHost = null;
        if (stand.equals(Stand.DEVELOP)) {
            mongoHost = G4Properties.getMongoProperties().getHostDevelop();
        } else if (stand.equals(Stand.MASTER)) {

            mongoHost = G4Properties.getMongoProperties().getHostMaster();
        } else  {
            ErrorReporter.raiseError("Unknown stand is passed:" + standString);

        }
        return mongoHost;
    }

    public static String getPort() {
        return G4Properties.getMongoProperties().getPort();

    }
}
