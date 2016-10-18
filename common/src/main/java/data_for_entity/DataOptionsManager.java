package data_for_entity;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.EntityDataProvider;
import data_for_entity.data_types.FieldDataType;
import data_for_entity.instance_managers.DefaultInstanceManager;
import utils.RandomGenerator;

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
    
    
    EntityDataProvider getDataProvider() {
        EntityDataProvider provider;
        Class<? extends EntityDataProvider> dataProviderClass = annotationReader.getDataProvider();
        FieldDataType fieldDataType = annotationReader.getFieldDataType();
        if (dataProviderClass != null) {
            provider = (EntityDataProvider)new DefaultInstanceManager().createInstance(dataProviderClass);
        } else if (fieldDataType != null) {
            provider = DataTypeManager.getDataTypeProvider(fieldDataType);
        } else {
            provider = null;
        }
        return provider;
    }
    
    /**
     * Reads an array of fieldNames current field is depends on.
     * @return {@link DataDependencies} instance that encapsulates {@link WithDataDependencies}
     */
    DataDependencies getDependencies() {
        DataDependencies dataDependencies;
        String[] dependencyFields = annotationReader.getDependencyFields();
        if (dependencyFields != null) {
            dataDependencies = new DataDependencies();
            dataDependencies.setFields(dependencyFields);
            dataDependencies.setProvider(annotationReader.getDependencyProvider());
        } else {
            dataDependencies = null;
        }
        return dataDependencies;
        
    }
    
    /**
     * .
     * Otherwise null is returned.
     * @return object
     */
    Integer getDataSize() {
        Integer dataSizeFromAnnotation = annotationReader.getDataSize();
        Integer dataSize;
        if (dataSizeFromAnnotation == null) {
            dataSize = RandomGenerator.generateRandomInteger(
                    FieldDataSizeOptions.getMinLength(), FieldDataSizeOptions.getMaxLength());
        } else {
            dataSize = dataSizeFromAnnotation;
        }
        return dataSize;
    }
    
    Integer getCollectionSize() {
        Integer collectionSizeFromAnnotation =  annotationReader.getCollectionSize();
        Integer collectionSize;
        if (collectionSizeFromAnnotation == null) {
            collectionSize = RandomGenerator.generateRandomInteger(
                    FieldDataSizeOptions.getMinCollectionLength(),
                    FieldDataSizeOptions.getMaxCollectionLength());
            
        } else {
            collectionSize = collectionSizeFromAnnotation;
        }
        return collectionSize;
    }
    
    
    
}
