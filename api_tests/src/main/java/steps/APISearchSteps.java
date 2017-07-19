package steps;

import http.OperationResult;
import json.JsonConverter;
import model.CBEntity;
import model.CBSearchFilter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.SearchService;

import java.util.List;
import java.util.regex.Pattern;

import static utils.StringUtils.fuzzySearch;

@SuppressWarnings("unchecked")
public class APISearchSteps extends APISteps {

    private static SearchService service = new SearchService();

    private String cbSearchQueryToRegex(String query) {
        StringBuilder sb = new StringBuilder();
        char[] chars = query.toCharArray();
        /*
            Example:
            g?t: matches "git", "get", "got", etc., but doesn't match "gate", "great", etc
            g*t: matches "git", "get', "got", "great", etc., but doesn't match "guilty", "gets", etc
            good\?: matches "good?", but not "goods", since ? is escaped and taken literally
            red\*: matched "red*", but not "reddish" for the same reason
        */
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

        return sb.toString();
    }

    @When("I send CB search request - query:$query, source:$source, objectType:$objectType, pageNumber:$pageNumber, pageSize:$pageSize")
    public void recordSearch(String query, String source, String objectType, String pageNumber, String pageSize) {

        CBSearchFilter filter = new CBSearchFilter(source, objectType, query, pageSize, pageNumber);

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> searchResults = operationResult.getEntity();

        context.put("searchResults", searchResults);
        context.put("searchQuery", query);
    }

    @Then("Profile entity list size $criteria than $size")
    public void profileListShouldBeLess(String criteria, String size) {
        List<CBEntity> entities = context.get("searchResults", List.class);

        switch (criteria) {
            case "more":
                Assert.assertTrue(
                        "Expected search results count " + criteria + " than " + size + ", but was:" + entities.size(),
                        entities.size() > Integer.valueOf(size));
                break;
            case "less":
                Assert.assertTrue(
                        "Expected search results count " + criteria + " than " + size + ", but was:" + entities.size(),
                        entities.size() < Integer.valueOf(size));
                break;
            default:
                throw new AssertionError("Unknown criteria value, expected:'less' or 'more', but was:" + criteria);
        }
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
