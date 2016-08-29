package app_context.entities;

import abs.EntityList;
import errors.NullReturnException;
import model.TargetGroup;

class TargetGroupsList extends EntityList<TargetGroup> {

    @Override
    public TargetGroup getEntity(String param) throws NullReturnException {
        for(TargetGroup group: entities) {
            if (group.getId().equals(param)){
                return group;
            }
        }
        throw new NullReturnException("There is not Target Group!");
    }
}
