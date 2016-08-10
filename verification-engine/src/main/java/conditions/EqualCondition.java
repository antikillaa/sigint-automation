package conditions;

import abs.TeelaEntity;
import errors.NullReturnException;
import json.JsonCoverter;
import model.Record;
import model.UIRecord;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

import static java.lang.Boolean.TRUE;

public class EqualCondition extends ExpectedCondition {

    private ExpectedCondition condition;
    private Logger log = Logger.getLogger(EqualCondition.class);


    @Override
    public String toString() {
        return condition.toString();
    }




    public EqualCondition elements(Object obj1, Object obj2) {
        condition = new ObjectEqualCondition(obj1, obj2);
        return this;
    }

    public EqualCondition elements(Set set1, Set set2) {
        condition = new SetEqualCondition(set1, set2);
        return this;
    }

    public <T extends TeelaEntity> EqualCondition elements(T obj1, T obj2) {
        condition = new TeelaEntityEqualCondition<T>(obj1, obj2);
        return this;
    }

    public EqualCondition elements(Record record, UIRecord uiRecord) {
        condition = new RecordToUIRecordEqualCondition(record, uiRecord);
        return this;
    }


    public Boolean check() {
        return condition.check();
    }

    private class SetEqualCondition extends ExpectedCondition {
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
            if ((set1==null || set1.size()==0) && (set2==null || set2.size()==0)) {
                return true;
            }
            return set1.equals(set2);
        }
    }

    private class ObjectEqualCondition extends ExpectedCondition {
        private Object obj1;
        private Object obj2;

        private ObjectEqualCondition(Object obj1, Object obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        @Override
        public String toString() {
            return "Object condition with object1: " + obj1.toString() + " and object2: " + obj2.toString();
        }

        protected Boolean check() {
            if ((obj1 == null) && (obj2 == null)) {
                return true;
            }
            String json1 = "";
            String json2 = "";
            try {
                json1 = JsonCoverter.toJsonString(obj1);
                json2 = JsonCoverter.toJsonString(obj2);
            } catch (NullReturnException e) {
                log.error(e.getMessage());
                log.error(e.getStackTrace());
            }
            return json1.equals(json2);
        }
    }

   private class TeelaEntityEqualCondition<T extends TeelaEntity> extends ExpectedCondition {
       private T obj1;
       private T obj2;

       private  TeelaEntityEqualCondition(T obj1, T obj2) {
           this.obj1 = obj1;
           this.obj2 = obj2;

       }

       public String toString() {
           return String.format("Compare two Teela entities: %s and %s", obj1.toString(), obj2.toString());
       }

       protected Boolean check() {
           log.debug("Comparing two teela entities with type:"+ obj2.getClass().getName());
           Boolean equals = TRUE;
           for (Field field: obj2.getClass().getDeclaredFields()) {
               if (Modifier.isStatic(field.getModifiers())) {
                   continue;
               }
               String originalValue;
               String requestValue;
               log.debug("Checking field with name:"+field.getName());
               try {
                   originalValue = BeanUtils.getProperty(obj1, field.getName());
                   log.debug("Value of object 1 is:"+originalValue);
                   requestValue = BeanUtils.getProperty(obj2, field.getName());
                   log.debug("Value of object 2 is:"+requestValue);
               } catch (Exception e) {
                   log.trace(e.getMessage(), e);
                   throw new AssertionError(e.getMessage());
               }
               if ((originalValue == null || originalValue.equals(""))  && (requestValue == null || requestValue.equals(""))) {
                   continue;
               }
               equals = originalValue.trim().equals(requestValue.trim());
               if (!equals) {
                   return equals;
               }
           }
           return equals;
       }
   }

    private class RecordToUIRecordEqualCondition extends ExpectedCondition {

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
        protected Boolean check() {
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



