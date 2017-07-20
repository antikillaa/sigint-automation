package steps;

import error_reporter.ErrorReporter;
import http.OperationResult;
import json.JsonConverter;
import model.CBEntity;
import model.CBSearchFilter;
import model.FileMeta;
import model.Source;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.SearchService;

import java.util.List;
import java.util.regex.Pattern;

import static utils.StringUtils.fuzzySearch;
import static utils.StringUtils.stringContainsAny;

@SuppressWarnings("unchecked")
public class APISearchSteps extends APISteps {

    private static SearchService service = new SearchService();

    /**
     * Example:
     * g?t: matches "git", "get", "got", etc., but doesn't match "gate", "great", etc
     * g*t: matches "git", "get', "got", "great", etc., but doesn't match "guilty", "gets", etc
     * good\?: matches "good?", but not "goods", since ? is escaped and taken literally
     * red\*: matched "red*", but not "reddish" for the same reason
     **/
    private String cbSearchQueryToRegex(String query) {
        StringBuilder sb = new StringBuilder().append(".*");

        char[] chars = query.toCharArray();

        for (int i = 0; i < query.length(); i++) {
            sb.append(chars[i]);
            if ((chars[i] == '?' || chars[i] == '*') && (i == 0 || chars[i - 1] != '\\')) {
                switch (chars[i]) {
                    case '?':
                        sb.replace(sb.length() - 1, sb.length(), ".");
                        break;
                    case '*':
                        sb.replace(sb.length() - 1, sb.length(), ".*");
                        break;
                }
            }
        }

        return sb.append(".*").toString();
    }

    @When("I send CB search request - query:$query, source:$source, objectType:$objectType, pageNumber:$pageNumber, pageSize:$pageSize")
    public void recordSearch(String query, String source, String objectType, String pageNumber, String pageSize) {

        CBSearchFilter filter = new CBSearchFilter(source, objectType, query, pageSize, pageNumber);

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> searchResults = operationResult.getEntity();

        context.put("searchResults", searchResults);
        context.put("searchQuery", query);
    }

    @Then("$size ingested records are searchable in CB")
    public void searchIngestedRecordsByProcessId(String size) {

        int expectedCount = Integer.valueOf(size);
        Source source = context.get("source", Source.class);
        String objectType = "event";
        if (stringContainsAny(source.getRecordType(), "CellTower", "PHONEBOOK", "Subscriber")) {
            objectType = "entity";
        }
        FileMeta meta = context.get("meta", FileMeta.class);
        String searchQuery = "pid:" + meta.getMeta().getProperties().getProcessId();

        CBSearchFilter filter = new CBSearchFilter(
                "SIGINT",
                objectType,
                searchQuery,
                "1000",
                "0"
        );

        int actualCount = 0;
        int delay = 10;  // in seconds
        for (int i = 0; i < 5; i++) {
            // 5 attempts with 10, 20, 40, 80, 160 seconds delay
            waitSeveralseconds(String.valueOf(delay));
            OperationResult<List<CBEntity>> operationResult = service.search(filter);
            List<CBEntity> searchResults = operationResult.getEntity();
            actualCount = searchResults.size();
            if (expectedCount == actualCount) {
                context.put("searchResults", searchResults);
                context.put("searchQuery", searchQuery);
                return;
            }
            delay = delay * 2;
        }
        String errorMsg = String.format("Expected %d %s-%s %s records in search, but was: %d",
                expectedCount,
                source.getType(),
                source.getRecordType(),
                objectType,
                actualCount);
        ErrorReporter.raiseError(errorMsg);
    }

    @Then("CB search result list size $criteria $size")
    public void CBSearchListSizeShouldBe(String criteria, String size) {
        List<CBEntity> entities = context.get("searchResults", List.class);

        int count = Integer.valueOf(size);
        boolean condition;

        switch (criteria) {
            case ">":
                condition = entities.size() > count;
                break;
            case "<":
                condition = entities.size() < count;
                break;
            case "==":
                condition = entities.size() == count;
                break;
            default:
                throw new AssertionError("Unknown criteria value, expected: >, < or ==");
        }
        Assert.assertTrue(
                "Expected search results count " + criteria + " " + size + ", but was: " + entities.size(),
                condition);
    }

    @Then("CB search results are correct")
    public void verifyCBSearch() {
        List<CBEntity> entities = context.get("searchResults", List.class);
        String query = context.get("searchQuery", String.class);

        String regex = cbSearchQueryToRegex(query);
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNIX_LINES);

        for (CBEntity entity : entities) {
            String json = JsonConverter.toJsonString(entity).replaceAll("(\\r\\n\\t|\\r\\n|\\n)", " ").trim();

            Boolean matches;
            if (query.contains("~")) {
                matches = fuzzySearch(json, query);
            } else {
                matches = pattern.matcher(json).matches();
            }

            Assert.assertTrue("Search query:" + query + ", doesn't matches this search result:\n" + json, matches);
        }
    }
}
