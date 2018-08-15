package ae.pegasus.framework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SearchQueueRequest extends AbstractEntity {

    private String uuid;
    private Boolean allowPartialResult;
    private Authorization authorization;
    private Highlight highlight = new Highlight();
    private SearchType searchType = SearchType.BASIC;
    private List<String> sourceType = new ArrayList<>(
            Stream.of("PROFILER", "INFORMATION_MANAGEMENT", "SIGINT", "GOVINT2", "GOVINT", "FININT", "OSINT", "CIO")
                    .collect(Collectors.toList()));
    private Page page = new Page();
    private List<String> objectType = new ArrayList<>();
    private String query;
    private String metadata;

    public List<String> getSourceType ()
    {
        return sourceType;
    }

    public void setSourceType (List<String> sourceType)
    {
        this.sourceType = sourceType;
    }

    public Page getPage ()
    {
        return page;
    }

    public void setPage (Page page)
    {
        this.page = page;
    }

    public String getQuery ()
    {
        return query;
    }

    public void setQuery (String query)
    {
        this.query = query;
    }

    public SearchType getSearchType ()
    {
        return searchType;
    }

    public void setSearchType (SearchType searchType)
    {
        this.searchType = searchType;
    }

    public List<String> getObjectType ()
    {
        return objectType;
    }

    public void setObjectType (List<String> objectType)
    {
        this.objectType = objectType;
    }

    public String getMetadata ()
    {
        return metadata;
    }

    public void setMetadata (String metadata)
    {
        this.metadata = metadata;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getAllowPartialResult() {
        return allowPartialResult;
    }

    public void setAllowPartialResult(Boolean allowPartialResult) {
        this.allowPartialResult = allowPartialResult;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Highlight getHighlight() {
        return highlight;
    }

    public void setHighlight(Highlight highlight) {
        this.highlight = highlight;
    }
}
			
			