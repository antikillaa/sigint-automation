package data_for_entity;

import data_for_entity.annotations.*;
import data_for_entity.data_providers.EntityDataProvider;
import data_for_entity.data_types.FieldDataType;
import data_for_entity.instance_managers.DefaultInstanceManager;

import java.lang.reflect.Field;

class DataOptionsManager {
    
    private AnnotationReader annotationReader;
    
    
    DataOptionsManager(Field field) {
        this.annotationReader = new AnnotationReader(field);
        
    }
    
    /**
     * Returns true if field is marked with annotation {@link DataIgnore}
     * @return Boolean if field is marked as DataIgnore, False otherwise.
     */
    Boolean shouldBeIgnored() {
        return annotationReader.isIgnorePresent();
    }
    
    
    boolean hasDependencies() {
        return annotationReader.getDependencies() != null;
    }
    
    boolean hasDataProvider() {
        return annotationReader.getDataProvider() != null;
    }
    
    boolean hasDataType() {
        return annotationReader.getFieldDataType() != null;
    }
    
    
    EntityDataProvider getDataProvider() {
        DataProvider dataProvider = annotationReader.getDataProvider();
        if (dataProvider != null) {
            return (EntityDataProvider)new DefaultInstanceManager().createInstance(dataProvider.value());
        }
        return null;
    }
    
    /**
     * Reads an array of fieldNames current field is depends on.
     * @return {@link DataDependencies} instance that encapsulates {@link WithDataDependencies}
     */
    DataDependencies getDependencies() {
        WithDataDependencies dataDependencies = annotationReader.getDependencies();
        return new DataDependencies(dataDependencies);
        
    }
    
    /**
     * Gets {@link WithDataSize} object if present and created {@link DataSize}.
     * Otherwise null is returned.
     * @return {@link DataSize} object
     */
    DataSize getDataSize() {
        WithDataSize dataOptions = annotationReader.getDataSize();
        return new DataSize(dataOptions);
    }
    
    
    FieldDataType getFieldDataType() {
        WithFieldDataType withFieldDataType = annotationReader.getFieldDataType();
        if (withFieldDataType !=null) {
            return withFieldDataType.value();
        }
        return null;
    }
    
    
}
