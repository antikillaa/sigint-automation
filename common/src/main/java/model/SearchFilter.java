package model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

public abstract class SearchFilter<T extends AbstractEntity> {

    @JsonIgnore
    protected SearchFilter<T> activeFilter;

    protected String sortField;
    protected Boolean sortDirection = true; //asc=true, desc=false

    protected Integer page = 0;
    protected Integer pageSize = 1000; //TODO

    protected void setActiveFilter(SearchFilter<T> activeFilter) {
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

    boolean isAppliedToFilterList(List<String> entityFieldCollection, List<String> filterCollection) {
        if (filterCollection == null || filterCollection.isEmpty()) {
            return true;
        } else if (entityFieldCollection == null || entityFieldCollection.isEmpty()) {
            return false;
        } else {
            entityFieldCollection.retainAll(filterCollection);
            return !entityFieldCollection.isEmpty();
        }
    }
}
