package model;

import java.util.List;

public class CBSearchResult {

    private Integer totalCount;
    private Integer pageSize;
    private Integer page;
    private List<CBEntity> data;
    private ResponseStatus status;

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

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public class ResponseStatus {

        private Integer httpStatusCode;
        private CodeStatus code;
        private String message;

        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        public CodeStatus getCode() {
            return code;
        }

        public void setCode(CodeStatus code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public enum CodeStatus {
        SUCCESS, FAILURE
    }
}