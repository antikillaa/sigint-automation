package data_for_entity.instance_managers;

import error_reporter.ErrorReporter;
import org.apache.log4j.Logger;

public class DefaultInstanceManager implements InstanceManager{
    
    private Logger logger = Logger.getLogger(DefaultInstanceManager.class);
    
    @Override
    public <T>T createInstance(Class<T> classType) throws Error {
        try {
            return  classType.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException | Error e) {
            logger.error("AAAAA ERROR CREATING INSTANCE 1111");
            ErrorReporter.reportAndRaiseError("Cannot create instance of class "+classType, e);
        }
        return null;
    }
}
