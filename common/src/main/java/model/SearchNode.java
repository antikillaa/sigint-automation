package model;

import java.util.List;

public class SearchNode {
    private String type;
    private List<String> words;
    private SearchFilters filters;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchFilters getFilters() {
        return filters;
    }

    public void setFilters(SearchFilters filters) {
        this.filters = filters;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
