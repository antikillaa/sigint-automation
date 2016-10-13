package data_for_entity;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import data_for_entity.data_providers.EntityDataProvider;
import data_for_entity.data_providers.StaticData;
import data_for_entity.data_types.FieldDataType;
import data_for_entity.instance_managers.DefaultInstanceManager;
import data_for_entity.instance_managers.InstanceManager;
import error_reporter.ErrorReporter;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;


class ObjectInitializer {
    
    private Logger logger = Logger.getLogger(ObjectInitializer.class);
    private TypeManager typeManager = new TypeManager();
    private InstanceManager instanceManager = new DefaultInstanceManager();
    private TasksExecutor executor = new TasksExecutor();
    
    
    void setInstanceManager(InstanceManager manager) {
        this.instanceManager = manager;
    }
    
    Object generateObjectFields(Class<?> objectType) {
        typeManager.setObjectType(objectType);
        Object instance = instanceManager.createInstance(objectType);
        logger.debug("Searching for required fields");
        List<ObjectField> requiredFields = typeManager.createFieldsFilter().filterByRequired(typeManager.getAllFields());
        if (requiredFields == null) {
            logger.debug("There are no required fields for entity: " + instance +
                    "Instance will be returned as it is");
            return instance;
        }
        for (ObjectField objectField:requiredFields) {
            FieldPopulator fieldPopulator = new FieldPopulator(objectField, instance);
            executor.submitTask(fieldPopulator);
            
        }
        Boolean tasksCompleted = executor.waitForCompletion();
        if (!tasksCompleted) {
            ErrorReporter.raiseError("Not all fields are generated! View debug logs for details");
        }
        logger.info("Object is created with filled fields");
        return instance;
    }
    
    
    private class FieldPopulator implements Runnable {
        
        private final ObjectField field;
        private Object instance;
        private DataOptionsManager fieldOptions;
        
        FieldPopulator(ObjectField objectField, Object instance) {
            this.field = objectField;
            this.instance = instance;
            this.fieldOptions = new DataOptionsManager(objectField.getField());
        }
    
        private  DependencyData createDependencyData(List<ObjectField> dependencyFields, Object instance) {
            DependencyData dependencyData = new DependencyData();
            for(ObjectField field: dependencyFields) {
                dependencyData.insertData(field.getName(), field.getValue(instance));
            }
            return dependencyData;
        }
        
        
        private List<ObjectField> getDependencyFields() {
            DataDependencies dependencies = fieldOptions.getDependencies();
            String[] dependenceFieldsNames = dependencies.getFields();
            logger.debug("Found dependencies for field. Dependencies: " + dependenceFieldsNames);
            List<ObjectField> dependenceFields = typeManager.getFields(dependenceFieldsNames);
            return dependenceFields;
        }
        
        
        private EntityDataProvider getProviderFromType(Class<?> classType) {
            Object value;
            
            if (Helpers.isCollection(classType)) {
                CollectionData data = new CollectionData(Helpers.getCollectionType(field.getField()));
                value = data.generateData(classType);
            } else if (Helpers.isMap(classType)) {
                MapData data = new MapData(Helpers.getMapTypes(field.getField()));
                value = data.generateData(classType);
            } else  {
                SimpleData data = new SimpleData();
                value = data.generateData(classType);
            }
            
            return new StaticData(value);
        }
        
        @Override
        public void run() {
            logger.debug("Generating value for required field:" + field.getName());
            synchronized (field) {
                if (field.getValue(instance) != null) {
                    logger.debug("Field: " + field.getName() + " already has value. Skipping...");
                    return;
                }
                EntityDataProvider provider;
                //if field has dependencies, first those fields will initialized with values
                if (fieldOptions.hasDependencies()) {
                    List<ObjectField> dependenceFields = getDependencyFields();
                    if (dependenceFields == null) {
                        logger.debug("Cannot fill in dependencies as fields are not found!");
                        logger.debug(String.format("Field %s is skipped", field.getName()));
                        return;
                    }
                    for (ObjectField field : dependenceFields) {
                        Task task = executor.submitTask(new FieldPopulator(field, instance));
                        task.waitToComplete();
                    }
                    DependencyDataProvider dependencyProvider = fieldOptions.getDependencies().getProvider();
                    dependencyProvider.setDependencyData(createDependencyData(dependenceFields, instance));
                    provider = dependencyProvider;
                } else if (fieldOptions.hasDataType()) {
                    provider = DataTypesProviders.getDataTypeProvider(fieldOptions.getFieldDataType());
                } else if (fieldOptions.hasDataProvider()) {
                    provider = fieldOptions.getDataProvider();
                } else {
                    provider = getProviderFromType(field.getField().getType());
                }
                
                //receiving data and assigning data to instance's field
                int dataLength = fieldOptions.getDataSize().getDataLength();
                Object data = provider.generate(dataLength);
                field.setValue(instance, data);
    
                }
            }
        
        
        private class SimpleData {
            Object generateData(Class<?> classType) {
                Object value;
                FieldDataType dataType = DataTypeByField.getDataType(classType);
                if (dataType.equals(FieldDataType.UNKNOWN)) {
                    value = generateObjectFields(classType);
                } else  {
                    value = DataTypesProviders.getDataTypeProvider(dataType)
                            .generate(fieldOptions.getDataSize().getDataLength());
                }
                return value;
            }
            
        }
        
        private class CollectionData extends SimpleData {
            
            private Class<?> collectionType;
    
            CollectionData(Class<?> collectionType) {
                this.collectionType = collectionType;
            }
            
            @Override
            Object generateData(Class<?> classType) {
                Collection collection;
                try {
                    collection = (Collection) instanceManager.createInstance(classType);
                } catch (Error er) {
                    logger.debug("Error occurred when creating instance of a collection. Might collection" +
                            " is abstract or is an interface. Examine logs for further details." +
                            "You must declare exact implementation class as a type!");
                    return null;
                }
                int collectionSize = fieldOptions.getDataSize().getCollectionSize();
                for (int i=1; i <= collectionSize; i++) {
                    collection.add(super.generateData(collectionType));
                }
                return classType.cast(collection);
            }
        }
        
        private class MapData extends SimpleData {
            
            private Class<?>[] typeClasses;
            
            MapData(Class<?>[] typeClasses) {
                this.typeClasses = typeClasses;
            }
            
            @Override
            Object generateData(Class<?> classType) {
                Map map;
                try {
                    map = (Map) instanceManager.createInstance(classType);
                } catch (Error er) {
                    logger.debug("Error occurred during creation of Map instance");
                    return null;
                }
                int collectionSize = fieldOptions.getDataSize().getCollectionSize();
                for (int i=1;i<=collectionSize;i++) {
                    Object key = super.generateData(typeClasses[0]);
                    Object value = super.generateData(typeClasses[1]);
                    map.put(key, value);
                }
                return map;
                        
            }
            
        }
    }
    
        
    
}
