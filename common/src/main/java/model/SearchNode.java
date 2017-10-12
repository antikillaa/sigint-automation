package model;

import java.util.List;

public class SearchNode {
    private String type;
    private List<String> words;
    private String firstWord;
    private String secondWord;
    private Integer distance;
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

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
