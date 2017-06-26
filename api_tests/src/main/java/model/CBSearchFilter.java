package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CBSearchFilter {

    private List<DataSourceCategory> sourceType = new ArrayList<>();
    private ObjectType objectType = ObjectType.entity;
    private String query = "";
    private Page page = new Page();

    public CBSearchFilter() {}

    public CBSearchFilter(String source, String query, String pageSize, String pageNumber) {
        this.query = query;
        this.setSourceType(Collections.singletonList(DataSourceCategory.valueOf(source)));

        if (pageSize.toLowerCase().equals("null")) {
            setPage(null);
        } else {
            setPageSize(Integer.valueOf(pageSize));
            setPageNumber(Integer.valueOf(pageNumber));
            setQuery(query);
        }
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<DataSourceCategory> getSourceType() {
        return sourceType;
    }

    public void setSourceType(List<DataSourceCategory> sourceType) {
        this.sourceType = sourceType;
    }

    public void setPageSize(int pageSize) {
        this.page.setPageSize(pageSize);
    }

    public void setPageNumber(int pageNumber) {
        this.page.setPageNumber(pageNumber);
    }

    class Page {
        private int pageSize;
        private int pageNumber;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }
    }
}
