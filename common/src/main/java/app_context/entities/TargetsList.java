package app_context.entities;

import abs.EntityList;
import errors.NullReturnException;
import model.Target;

class TargetsList extends EntityList<Target> {

    @Override
    public Target getEntity(String param) throws NullReturnException {
        for (Target target:entities) {
            if (target.getId().equals(param)) {
                return target;
            }
        }
        throw new NullReturnException("Target is not found!");
    }
}
