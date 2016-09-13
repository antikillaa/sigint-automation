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

    /**
     * Get matching result by Target name and TargetResultType
     * @param name target name
     * @param targetResultType TargetResultType
     * @return MatchingResult or null if none found
     */
    public MatchingResult findMatchingResultByTargetNameAndTargetResultType(String name, TargetResultType targetResultType) {
        List<MatchingResult> matchingResults = searchResults;
        for (MatchingResult result : matchingResults) {
            if (result.getTarget().getName().equals(name) && result.getTargetResultType().equals(targetResultType)) {
                return result;
            }
        }
        return null;
    }
}
