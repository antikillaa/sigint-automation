package app_context.entities;

import abs.EntityList;
import errors.NullReturnException;
import model.DuSubscriberEntry;

class DuSubscriberList extends EntityList<DuSubscriberEntry> {


    @Override
    public DuSubscriberEntry getEntity(String param) throws NullReturnException {
        for(DuSubscriberEntry entry : entities) {
            if (entry.getId().equals(param)){
                return entry;
            }
        }
        throw new NullReturnException("There is not DuSubscriberEntry!");
    }
}
