package model;

import java.util.List;

public class UploadDetails {

    private Process process;
    private String matching;
    private List<MatchingResult> searchResults;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getMatching() {
        return matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public List<MatchingResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<MatchingResult> searchResults) {
        this.searchResults = searchResults;
    }

}
