package model.entities;

import errors.NullReturnException;
import model.Profile;

class ProfileList extends EntityList<Profile> {

    @Override
    public Profile getEntity(String name) throws NullReturnException {

        for (Profile entity : entities) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }

        throw new NullReturnException("Profile not found by name:" + name);
    }
}
