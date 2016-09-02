package entity_fabric;

import entity_fabric.entity_fields_annotations.Required;
import error_reporter.ErrorReporter;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class  TeelaInstance<T> {
    
    private T teelaInstance;
    private Logger logger = Logger.getLogger(TeelaInstance.class);
    
    private void createInstance(Class<T> teelaClass) {
        try {
            T instance = teelaClass.newInstance();
            this.teelaInstance = instance;
        } catch (InstantiationException | IllegalAccessException e) {
            ErrorReporter.reportError(e);
            this.teelaInstance = null;
            
        }
    
    }
    
    public void fillInAllFields() {
    };
    
    public T fillOnlyRequiredFields() {
        if (teelaInstance == null) {
            logger.warn("Cannot fill mandatory fields for teela instance as it " +
                    "wasn't created");
            return teelaInstance;
        }
        List<Field> requiredOnlyFields = getInstanceRequiredFields();
        if (requiredOnlyFields.size() == 0) {
            logger.warn("There are no fields that are marked as required." +
                    "Instance will be returned with default values based on data type");
            return teelaInstance;
        }
        for (Field field: requiredOnlyFields) {
            
        }
        
        
        
    }
    
    private List<Field> getInstanceRequiredFields() {
        Field[] fields = teelaInstance.getClass().getDeclaredFields();
        List<Field> requiredOnlyFields = new ArrayList<>();
        for (Field field:fields) {
            if (field.isAnnotationPresent(Required.class)) {
                requiredOnlyFields.add(field);
            }
        }
        return requiredOnlyFields;
        
    }
    
}

