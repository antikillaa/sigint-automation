package ae.pegasus.framework.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class SavedSearchMetadata {

    private String query;
    private String type;
    private SearchFilters filters;
    private List<AdvancedSearch> advancedSearch;

    public SearchFilters getFilters() {
        return filters;
    }

    public void setFilters(SearchFilters filters) {
        this.filters = filters;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AdvancedSearch> getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(List<AdvancedSearch> advancedSearch) {
        this.advancedSearch = advancedSearch;
    }
}
