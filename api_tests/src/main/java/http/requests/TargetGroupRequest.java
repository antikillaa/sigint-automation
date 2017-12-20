package http.requests;

import http.HttpMethod;
import model.SearchFilter;
import model.TargetGroup;

public class TargetGroupRequest extends HttpRequest {

    private static final String URI = "/api/profiler/targetGroups";

    public TargetGroupRequest() {
        super(URI);
    }

    public TargetGroupRequest add(TargetGroup targetGroup) {
        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(targetGroup);
        return this;
    }

    /**
     * GET /targetGroups/{groupId} getGroup
     *
     * @param id groupId
     * @return {@link TargetGroupRequest}
     */
    public TargetGroupRequest get(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    public TargetGroupRequest delete(String id) {
        this
                .setURI(URI + "/" + id)
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public TargetGroupRequest update(TargetGroup targetGroup) {
        this
                .setHttpMethod(HttpMethod.POST)
                .setPayload(targetGroup);
        return this;
    }

    public TargetGroupRequest list(SearchFilter filter) {
        String params = "/root?page=" + filter.getPage() +
                "&pageSize=" + filter.getPageSize() +
                "&sortKey=" + filter.getSortField() +
                "&sortOrder=" + (filter.isSortDirection() ? "ASC" : "DESC");
        this
                .setURI(URI + params)
                .setHttpMethod(HttpMethod.GET);
        return this;
    }

    /**
     * POST /targetGroups/{groupId}/targetGroups createChildGroup
     *
     * @param parentId   parent group id
     * @param childGroup new child group
     * @return {@link TargetGroupRequest}
     */
    public TargetGroupRequest createChildGroup(String parentId, TargetGroup childGroup) {
        this
                .setURI(URI + "/" + parentId + "/targetGroups")
                .setHttpMethod(HttpMethod.POST)
                .setPayload(childGroup);
        return this;
    }

    /**
     * GET /targetGroups/{groupId}/contents
     *
     * @param groupID groupID
     * @return {@link TargetGroupRequest}
     */
    public TargetGroupRequest getContent(String groupID) {
        this
                .setURI(URI + "/" + groupID + "/contents")
                .setHttpMethod(HttpMethod.GET);
        return this;
    }
}
