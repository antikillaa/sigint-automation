package service; /**
 * Created by dm on 3/11/16.
 */

import model.EntityList;
import model.SearchFilter;
import model.TeelaEntity;


public interface EntityService <T extends TeelaEntity> {

    public int addNew(T entity);
    public int remove(T entity);
    public EntityList<T> list(SearchFilter filter);
    public int update(T entity);
    public T view(String id);
}
