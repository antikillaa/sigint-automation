package http.requests;

import http.HttpMethod;
import model.FinderFile;
import model.SearchFilter;
import model.TargetGroupSearchFilter;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FinderFileRequest extends HttpRequest {

    private static final String URI = "/api/file-system/files";
    private static final Logger log = Logger.getLogger(FinderFileRequest.class);

    public FinderFileRequest() {
        super(URI);
    }

    public FinderFileRequest add(FinderFile finderFile) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(finderFile);
        return this;
    }

    public FinderFileRequest get(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public FinderFileRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public FinderFileRequest update(FinderFile finderFile) {
        this
                .setHttpMethod(HttpMethod.POST)
                .setPayload(finderFile);
        return this;
    }

    public FinderFileRequest list(SearchFilter filter) {
        String params = "/root?page=" + filter.getPage() +
                "&pageSize=" + filter.getPageSize() +
                "&sortKey=" + filter.getSortField() +
                "&sortOrder=" + (filter.isSortDirection() ? "ASC" : "DESC");
        this
                .setURI(URI + params)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public FinderFileRequest search(SearchFilter searchFilter) {
        TargetGroupSearchFilter filter = (TargetGroupSearchFilter) searchFilter;
        String params;
        try {
            params = "/search?page=" + filter.getPage() +
                    "&pageSize=" + filter.getPageSize() +
                    (filter.getQuery().isEmpty() ? "" : "&query=" + URLEncoder.encode(filter.getQuery(), "UTF-8")) +
                    "&sortKey=" + filter.getSortField() +
                    "&sortOrder=" + (filter.isSortDirection() ? "ASC" : "DESC");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            throw new AssertionError(e);
        }
        this
                .setURI(URI + params)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public FinderFileRequest getContent(String fileID) {
        this
                .setURI(URI + "/" + fileID + "/contents")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
