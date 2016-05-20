package service;

import abs.EntityList;
import abs.SearchFilter;
import abs.TeelaEntity;


public interface EntityService <T extends TeelaEntity> {

    public int addNew(T entity);
    public int remove(T entity);
    public EntityList<T> list(SearchFilter filter);
    public int update(T entity);
    public T view(String id);
}
