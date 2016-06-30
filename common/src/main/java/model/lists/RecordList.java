package model.lists;

import abs.EntityList;
import errors.NullReturnException;
import model.Record;

public class RecordList extends EntityList<Record> {


    @Override
    public Record getEntity(String param) throws NullReturnException {
        return null;
    }
}
