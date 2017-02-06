package conditions;

import abs.TeelaEntity;
import http.JsonConverter;
import model.Record;
import model.UIRecord;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import utils.Parser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class EqualCondition implements ExpectedCondition {

    private List<ExpectedCondition> conditions = new ArrayList<>();
    private Logger log = Logger.getLogger(EqualCondition.class);


    @Override
    public String toString() {
        return conditions.toString();
    }


    void elements(Object obj1, Object obj2) {
        ExpectedCondition equalCondition;
        if (obj1 == null || obj2 == null) {
            equalCondition = new ObjectEqualCondition(obj1, obj2);
            conditions.add(equalCondition);
            return;
        }
        if (!obj1.getClass().equals(obj2.getClass())) {
            equalCondition = new NotEqual();
        }
        if (Collection.class.isAssignableFrom(obj1.getClass())) {
            elements((Collection) obj1, (Collection) obj2);
            return;
        } else if (TeelaEntity.class.isAssignableFrom(obj1.getClass())) {
            equalCondition = new TeelaEntityEqualCondition<>((TeelaEntity) obj1, (TeelaEntity) obj2);

        } else {
            equalCondition = new ObjectEqualCondition(obj1, obj2);
        }
        conditions.add(equalCondition);

    }

    private void elements(Collection collection1, Collection collection2) {
        if (collection1.size() != collection2.size()) {
            conditions.add(new NotEqual());
            return;
        }
        Object[] collection1ToArray = collection1.toArray();
        Object[] collection2ToArray = collection2.toArray();
        for (int i = 0; i < collection1.size() - 1; i++) {
            elements(collection1ToArray[i], collection2ToArray[i]);
        }

    }


    public Boolean check() {
        Boolean isConditionApplied = true;
        for (ExpectedCondition condition : conditions) {
            isConditionApplied = condition.check();
            if (!isConditionApplied) {
                return isConditionApplied;
            }
        }
        return true;
    }

    private class NotEqual implements ExpectedCondition {

        @Override
        public String toString() {
            return "Stub condition for false";
        }

        public Boolean check() {
            return false;
        }
    }

    private class SetEqualCondition implements ExpectedCondition {
        private Collection set1;
        private Collection set2;

        private SetEqualCondition(Collection set1, Collection set2) {
            this.set1 = set1;
            this.set2 = set2;
        }

        public String toString() {
            return "Collection condition with collection1: " + set1 + " and collection2: " + set2;
        }

        public Boolean check() {
            if ((set1 == null || set1.size() == 0) && (set2 == null || set2.size() == 0)) {
                return true;
            }
            return set1.equals(set2);
        }
    }

    private class ObjectEqualCondition implements ExpectedCondition {
        private Object obj1;
        private Object obj2;

        private ObjectEqualCondition(Object obj1, Object obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        @Override
        public String toString() {
            return "Object condition with object1: " + Parser.entityToString(obj1) + " and object2: " + Parser.entityToString(obj2);
        }

        public Boolean check() {
            if ((obj1 == null) && (obj2 == null)) {
                return true;
            }
            String json1 = JsonConverter.toJsonString(obj1);
            String json2 = JsonConverter.toJsonString(obj2);

            return json1.equals(json2);
        }
    }

    private class TeelaEntityEqualCondition<T extends TeelaEntity> implements ExpectedCondition {
        private T obj1;
        private T obj2;

        private TeelaEntityEqualCondition(T obj1, T obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;

        }

        public String toString() {
            return String.format("Compare two Teela entities: %s and %s", obj1.toString(), obj2.toString());
        }

        public Boolean check() {
            log.debug("Comparing two teela entities with type:" + obj2.getClass().getName());
            Boolean equals = TRUE;
            for (Field field : obj2.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Object originalValue;
                Object requestValue;
                log.debug("Checking field with name:" + field.getName());
                try {
                    originalValue = field.get(obj1);
                    log.debug("Value of object 1 is:" + originalValue.toString());
                    requestValue = field.get(obj2);
                    log.debug("Value of object 2 is:" + requestValue.toString());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new AssertionError(e.getMessage());
                }

                // both null or empty string
                if ((originalValue == null || originalValue.equals("")) && (requestValue == null || requestValue.equals(""))) {
                    continue;
                }
                // if one of them null return false, else equals them
                equals = !(originalValue == null || requestValue == null) &&
                        originalValue.equals(requestValue);

                if (!equals) {
                    return false;
                }
            }
            return true;
        }
    }

    private class RecordToUIRecordEqualCondition implements ExpectedCondition {

        private Record record;
        private UIRecord uiRecord;


        public RecordToUIRecordEqualCondition(Record record, UIRecord uiRecord) {
            this.record = record;
            this.uiRecord = uiRecord;
        }

        @Override
        public String toString() {
            return String.format("Comparing Record %s with record parsed from UI %s", record, uiRecord);
        }

        @Override
        public Boolean check() {
            String.format("Checking records...");
            return record.getTmsi().equals(uiRecord.getTmsi()) &&
                    record.getLanguage().equals(uiRecord.getLanguage()) &&
                    record.getImsi().equals(uiRecord.getImsi()) &&
                    record.getDateAndTime().equals(uiRecord.getDateAndTime()) &&
                    record.getProcessedStatus().equals(uiRecord.getProcessedStatus()) &&
                    record.getOriginalId().equals(uiRecord.getOriginalId()) &&
                    record.getDuration() == uiRecord.getDuration() &&
                    record.getText().equals(uiRecord.getText());
        }
    }
}



