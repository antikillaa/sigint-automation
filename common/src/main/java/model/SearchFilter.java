package model;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class SearchFilter <T extends AbstractEntity> {

    @JsonIgnore
    protected SearchFilter activeFilter;

    protected String sortField;
    protected Boolean sortDirection = true; //asc=true, desc=false

    protected Integer page = 0;
    protected Integer pageSize = 1000; //TODO

    protected SearchFilter getActiveFilter() {
        return activeFilter;
    }

    protected void setActiveFilter(SearchFilter activeFilter) {
        this.activeFilter = activeFilter;
    }

    public abstract boolean isAppliedToEntity(T entity);


    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean isSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Boolean sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
