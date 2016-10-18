package data_for_entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

/**
 * Util class to do background operation for {@link Field}
 */
class Helpers {
    
    static boolean isCollection(Class<?> userClass) {
        return Collection.class.isAssignableFrom(userClass) ||
                Array.class.isAssignableFrom(userClass);
    }
    
    static boolean isMap(Class<?> userClass) {
        return Map.class.isAssignableFrom(userClass);
    }
    
    @SuppressWarnings("unchecked")
    static<T> T getAnnotationDefault(Class<? extends Annotation> annotationClass, String element) throws Exception {
        Method method = annotationClass.getMethod(element,(Class[])null);
        return((T)method.getDefaultValue());
    }
    
    static Class<?> getCollectionType(Field field) {
        Class<?> innerClass;
        Class<?> collectionClass = field.getType();
        if (Array.class.isAssignableFrom(collectionClass)) {
            innerClass = collectionClass.getComponentType();
        } else  if (Collection.class.isAssignableFrom(collectionClass)) {
            ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
            innerClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        } else {
            innerClass = null;
        }
        return innerClass;
    }
    
    static Class<?>[] getMapTypes(Field field) {
        Class<?>[] classTypes;
        classTypes = new Class<?>[2];
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        Type[] types = stringListType.getActualTypeArguments();
        classTypes[0] = (Class<?>)types[0];
        classTypes[1] = (Class<?>)types[1];
        return classTypes;
    }
    
    
}
