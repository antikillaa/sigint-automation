package steps;

import errors.NullReturnException;
import model.AppContext;
import model.InformationRequest;
import org.apache.log4j.Logger;

/**
 * Created by dm on 5/16/16.
 */
public class APISteps {

    private static AppContext context = AppContext.getContext();
    private static Logger log = Logger.getRootLogger();

    public static InformationRequest getRFIfromContext() {


        try {
            return context.getFromRunContext("createdRFI", InformationRequest.class);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }

    }

}
