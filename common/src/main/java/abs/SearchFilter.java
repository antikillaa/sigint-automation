package abs;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class SearchFilter <T extends TeelaEntity> {

    @JsonIgnore
    protected SearchFilter activeFilter;

    protected String sortField;
    protected boolean sortDirection; //asc=true, desc=false

    protected int page = 0;
    protected int pageSize = 100;

    public SearchFilter getActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(SearchFilter activeFilter) {
        this.activeFilter = activeFilter;
    }

    public abstract boolean filter(T entity);


    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(boolean sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
