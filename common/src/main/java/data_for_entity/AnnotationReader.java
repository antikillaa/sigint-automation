package data_for_entity;

import data_for_entity.annotations.*;
import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_providers.DependencyDataProvider;
import data_for_entity.data_providers.EntityDataProvider;
import data_for_entity.data_types.FieldDataType;
import error_reporter.ErrorReporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Encapsulates the way field's options are loaded into system.
 */
class AnnotationReader {
    
    private Field field;
    
    
    AnnotationReader(Field field) {
        this.field = field;
    }
    
    
    /**
     * Returns true if field is marked with annotation {@link DataIgnore}
     * @return Boolean if field is marked as DataIgnore, False otherwise.
     */
    Boolean isIgnorePresent() {
        return field.isAnnotationPresent(DataIgnore.class);
    }
    
    
    
    /**
     * Reads an array of fieldNames current field is depends on.
     * @return Array of field names or null if there are no dependencies.
     */
    DependencyDataProvider getDependencyProvider() {
        WithDataDependencies dataDependencies = readAnnotation(WithDataDependencies.class);
        if (dataDependencies!=null) {
            dataDependencies.provider();
        }
        return null;
        
    }
    
    String[] getDependencyFields() {
        WithDataDependencies dataDependencies = readAnnotation(WithDataDependencies.class);
        if (dataDependencies!=null) {
            return dataDependencies.fields();
        }
        return null;
    }
    
    /**
     * Gets {@link WithDataSize} object if present.
     * Otherwise null is returned.
     * @return {@link WithDataSize} object or null if not present
     * or error occurred while processing.
     */
    Integer getDataSize() {
        WithDataSize dataOptions = readAnnotation(WithDataSize.class);
        if (dataOptions!=null) {
            return dataOptions.value();
        }
        return null;
    }
    
    Integer getCollectionSize() {
        WithCollectionSize collectionSize = readAnnotation(WithCollectionSize.class);
        if (collectionSize!=null) {
            return collectionSize.value();
        }
        return null;
    }
    
    Class<? extends EntityDataProvider> getDataProvider() {
        DataProvider dataProvider = readAnnotation(DataProvider.class);
        if (dataProvider!=null) {
            return dataProvider.value();
        }
        return null;
    }
    
    FieldDataType getFieldDataType() {
        WithFieldDataType dataType = readAnnotation(WithFieldDataType.class);
        if (dataType !=null) {
            dataType.value();
        }
        return null;
    }
    
    /**
     * Internal method to read annotation object from field.
     * @param annotationClass Annotation class from {@link data_for_entity.annotations}
     * @param <T> object with type annotationClass
     * @return object with type annotationClass or null if annotation is not present
     * or error occured while downcasting to type annotationClass.
     */
    private <T extends Annotation>T readAnnotation(Class<T> annotationClass) {
        boolean annotationPresent = field.isAnnotationPresent(annotationClass);
        if (!annotationPresent) {
            return null;
        }
        try {
            Annotation annotation = field.getAnnotation(annotationClass);
            return annotationClass.cast(annotation);
        } catch (ClassCastException e) {
            ErrorReporter.reportError(e);
            return null;
        }
    }
}
