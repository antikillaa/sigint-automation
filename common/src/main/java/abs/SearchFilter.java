package abs;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class SearchFilter <T extends TeelaEntity> {

    @JsonIgnore
    protected SearchFilter activeFilter;


    public SearchFilter getActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(SearchFilter activeFilter) {
        this.activeFilter = activeFilter;
    }

    public abstract boolean filter(T entity);

}
