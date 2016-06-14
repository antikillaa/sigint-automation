package model.lists;

import abs.EntityList;
import errors.NullReturnException;
import model.Phonebook;

public class PhonebookList extends EntityList<Phonebook> {

    @Override
    public Phonebook getEntity(String param) throws NullReturnException {
        return null;
    }
}
