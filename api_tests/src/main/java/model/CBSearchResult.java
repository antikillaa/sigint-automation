package model;

import java.util.List;

public class CBSearchResult {

    private Integer totalCount;
    private Integer pageSize;
    private Integer page;
    private List<CBEntity> data;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<CBEntity> getData() {
        return data;
    }

    public void setData(List<CBEntity> data) {
        this.data = data;
    }
}
