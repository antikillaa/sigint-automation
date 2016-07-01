package services;

import abs.EntityList;
import abs.SearchFilter;
import model.Source;
import service.EntityService;

public class SourceService implements EntityService<Source> {

    @Override
    public int add(Source entity) {
        return 0;
    }

    @Override
    public int remove(Source entity) {
        return 0;
    }

    @Override
    public EntityList<Source> list(SearchFilter filter) {
        return null;
    }

    @Override
    public int update(Source entity) {
        return 0;
    }

    @Override
    public Source view(String id) {
        return null;
    }
}
