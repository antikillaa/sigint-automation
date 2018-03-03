package steps;

import http.OperationResult;
import http.OperationsResults;
import json.JsonConverter;
import model.*;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.SearchService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static error_reporter.ErrorReporter.raiseError;
import static json.JsonConverter.toJsonString;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.apache.commons.lang3.StringUtils.getLevenshteinDistance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static utils.DateHelper.*;
import static utils.SearchQueryBuilder.*;
import static utils.StepHelper.compareByCriteria;
import static utils.StringUtils.extractStringsInQuotes;

@SuppressWarnings("unchecked")
public class APISearchSteps extends APISteps {

    private static SearchService service = new SearchService();
    private static final String processIdQuery = "pid:";
    private static final Integer UNIMPORTANT = -1;

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

        return sb.append(".*").toString().replace(".*.*", ".*");
    }

    @When("I send CB search request - query:$query, source:$source, objectType:$objectType, pageNumber:$pageNumber, pageSize:$pageSize")
    public void recordSearch(String query, String source, String objectType, String pageNumber, String pageSize) {

        CBSearchFilter filter = new CBSearchFilter(source, objectType, query, pageSize, pageNumber);

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> searchResults = operationResult.getEntity();

        context.put("searchResults", searchResults);
        context.put("searchQuery", query);
    }

    private void searchIngestedRecords(String objectType, String criteria, String size, String additionalQuery) {
        int expectedCount = Integer.valueOf(size);

        Source source = context.get("source", Source.class);
        FileMeta meta = context.get("meta", FileMeta.class);
        String searchQuery = processIdQuery + meta.getMeta().getProperties().getProcessId();
        if (additionalQuery != null) {
            searchQuery = searchQuery + " " + additionalQuery;
        }

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

            if (compareByCriteria(criteria, actualCount, expectedCount)) {
                context.put("searchResults", searchResults);
                context.put("searchQuery", searchQuery);
                return;
            }
            delay = delay * 2;
        }
        String errorMsg = String.format(
                "Found %d %s-%s %s records in search, expected %s %d",
                actualCount, source.getType(), source.getRecordType(), objectType, criteria, expectedCount);
        raiseError(errorMsg);
    }

    @Then("Number of ingested $objectType records in CB $criteria $size, additional query string: $additional")
    public void searchIngestedRecordsByProcessIdWithOptions(String objectType, String criteria, String size, String additional) {
        searchIngestedRecords(objectType, criteria, size, additional);
    }

    @Then("Number of ingested $objectType records in CB $criteria $size")
    public void searchIngestedRecordsByProcessId(String objectType, String criteria, String size) {
        searchIngestedRecords(objectType, criteria, size, null);
    }

    @Then("CB search result list size $criteria $size")
    public void CBSearchListSizeShouldBe(String criteria, String size) {
        List<CBEntity> entities = context.get("searchResults", List.class);

        int expectedCount = Integer.valueOf(size);
        boolean condition = compareByCriteria(criteria, entities.size(), expectedCount);
        assertTrue("Expected search results count " + criteria + " " + size + ", but was: " + entities.size(), condition);
    }

    @Then("All events have default designation")
    public void checkDefaultDesignation() {
        String defaultDesignation = "Undesignated";

        log.info("Check that all records have default designation: " + defaultDesignation);
        context.put("searchQuery", defaultDesignation);
        verifyCBSearch("contains");
    }

    @Then("Designated events have correct designations")
    public void designationsArePresentedInCBSearch() {
        List<DesignationMapping> designationMappings = context.get("designationMappingList", List.class);
        FileMeta meta = context.get("meta", FileMeta.class);
        String pid = processIdQuery + meta.getMeta().getProperties().getProcessId();

        int designatedEvents = 0;
        for (DesignationMapping designationMapping : designationMappings) {
            List<String> designations = designationMapping.getDesignations();
            assertNotNull("Designation-mapping with null designations: " + designationMapping.getId(), designations);

            String identifier = designationMapping.getIdentifier();
            recordSearch(
                    "senderphone:" + identifier + " AND includeSpam:true AND " + pid,
                    "SIGINT",
                    "event",
                    "0",
                    "100"
            );
            List<CBEntity> entities = context.get("searchResults", List.class);
            if (entities.isEmpty()) {
                // skip not designated records
                continue;
            }
            designatedEvents++; // increment if search result isn't empty
            for (String designation : designations) {
                log.info("Check '" + designation + "' designation in response");
                for (CBEntity entity : entities) {
                    String json = toJsonString(entity);
                    if (!json.contains(designation)) {
                        raiseError("Event doesn't have '" + designation + "' designation:\n" + json);
                    }
                }
            }
        }
        if (designatedEvents < 1) {
            raiseError("Records aren't designated");
        }
    }

    @Then("Whitelisted identifiers are not searchable")
    public void whitelistedCBSearch() {
        List<Whitelist> filteredWhitelists = context.get("whitelistEntitiesList", List.class);

        log.info("Check that identifiers are filtered from CB search by pid");
        for (Whitelist filteredWhitelist : filteredWhitelists) {
            String identifier = filteredWhitelist.getIdentifier();
            context.put("searchQuery", identifier);
            verifyCBSearch("doesn't contain");
        }
        log.info("Check that identifier entities are not created");
        for (Whitelist filteredWhitelist : filteredWhitelists) {
            String identifier = filteredWhitelist.getIdentifier();
            recordSearch(identifier, "SIGINT", "entity", "0", "100");
            CBSearchListSizeShouldBe("==", "0");
        }
    }

    @Then("CB search $criteria results for query")
    public void verifyCBSearch(String criteria) {
        log.info("CB search result validation");

        boolean searchable = false;
        if (criteria.equalsIgnoreCase("contains")) {
            searchable = true;
        }
        List<CBEntity> entities = context.get("searchResults", List.class);
        String query = context.get("searchQuery", String.class);


        String regex = cbSearchQueryToRegex(query);
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNIX_LINES);

        for (CBEntity entity : entities) {
            String json = toJsonString(entity).replaceAll("(\\r\\n\\t|\\r\\n|\\n)", " ").trim();

            Boolean matches;
            if (query.contains("~")) {
                matches = fuzzySearch(json, query);
            } else {
                matches = pattern.matcher(json).matches();
            }
            assertTrue("Search " + criteria + " results for query: " + query + " in response:\n" + json,
                    matches == searchable);
        }
    }

    @Then("pageSize size in response $criteria $size")
    public void pageSizeInResponseShouldBe(String criteria, String size) {

        OperationResult result = OperationsResults.getResult();
        CBSearchResult cbSearchResult = JsonConverter.jsonToObject(result.getMessage(), CBSearchResult.class);
        Integer pageSize = cbSearchResult.getPageSize();

        int expectedCount = Integer.valueOf(size);
        boolean condition = compareByCriteria(criteria, pageSize, expectedCount);

        assertTrue("Expected pageSize size " + criteria + " " + size + ", but was: " + pageSize, condition);
    }

    private boolean fuzzySearch(String json, String query) {

        if (query.contains("~")) {
            Integer tildaIndex = query.indexOf("~");
            String q = query.substring(0, tildaIndex);

            Integer maxDistance = 3;
            String suffix = query.substring(tildaIndex + 1);
            if (!suffix.isEmpty()) {
                maxDistance = Integer.valueOf(suffix);
            }

            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> flattenJSON = new JsonConverter().flattenJsonMap(json);

            for (String value : flattenJSON.values()) {
                String[] strings = value.split("\\s+|\\n+|\\W+");
                for (String s : strings) {
                    Integer distance = getLevenshteinDistance(s.toLowerCase(), q.toLowerCase());
                    distances.put(value.toLowerCase(), distance);
                    if (distance <= maxDistance)
                        return true;
                }
            }

            log.error("Pattern not founded, LevenshteinDistances:" + distances + " maxDistance:" + maxDistance);
        } else {
            log.error("Query string isn't fuzzy");
        }

        return false;
    }

    @When("I send workflow search request: record status:$recordStatus, source:$source, objectType:$objectType, pageNumber:$pageNumber, pageSize:$pageSize")
    public void searchWorkflowFiltersRecordStatus(String recordStatus, String source, String objectType, String pageNumber, String pageSize) {
        String query = recordStatusToQuery(recordStatus);
        CBSearchFilter filter = new CBSearchFilter(source, objectType, query, pageSize, pageNumber);

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> searchResults = operationResult.getEntity();

        context.put("searchResults", searchResults);
        context.put("recordStatusFilter", recordStatus);
    }

    @Then("CB search results match the recordStatus filters")
    public void matchSearchResultsWithFilters() {
        List<CBEntity> entities = context.get("searchResults", List.class);
        String[] recordStatuses = context.get("recordStatusFilter", String.class).split("\\s+");

        for (CBEntity entity : entities) {
            for (String recordStatus : recordStatuses) {
                switch (recordStatus.toLowerCase()) {
                    case "unassigned":
                        assertTrue("Search by RecordStatus:Unassigned, return assigned record:" + toJsonString(entity),
                                entity.getAssignments().getOwnerId().isEmpty());
                        break;
                    case "unprocessed":
                        assertTrue("Search by RecordStatus::Unprocessed return record with reportIds:" + toJsonString(entity),
                                entity.getReports() == null || entity.getReports().getReportIds().isEmpty());
                        assertFalse("Search by RecordStatus:Unprocessed return Unimportance record:" + toJsonString(entity),
                                Objects.equals(entity.getAssignments().getImportance(), UNIMPORTANT));
                        break;
                    case "reported":
                        assertFalse("Search by RecordStatus:Reported return record without reportIds:" + toJsonString(entity),
                                entity.getReports().getReportIds().isEmpty());
                        break;
                    case "unimportant":
                        assertTrue("Search by RecordStatus:Unimportant return NOT unimportance record:" + toJsonString(entity),
                                Objects.equals(entity.getAssignments().getImportance(), UNIMPORTANT));
                        break;
                    default:
                        throw new AssertionError("Unknown recordStatus filter value: " + recordStatus);
                }
            }
        }
    }

    @Then("CB search results contains only sourceType:$sourceType and objectType:$objectType records")
    public void cbSearchResultsContainsOnlySourceTypeAndObjectTypeRecords(String sourceType, String objectType) {
        List<CBEntity> entities = context.get("searchResults", List.class);

        CBEntity entity = entities.stream()
                .filter(cbEntity -> !cbEntity.getSourceType().equals(sourceType))
                .findAny().orElse(null);

        assertNull("Search by sourceType:" + sourceType + "return:" + toJsonString(entity), entity);

        if (!sourceType.equals("PROFILER")) {
            entity = entities.stream()
                    .filter(cbEntity -> !cbEntity.getObjectType().name().equals(objectType))
                    .findAny().orElse(null);

            assertNull("Search by object type:" + objectType + "return:" + toJsonString(entity), entity);
        }
    }

    @Then("CB search results contains only sourceType from query")
    public void cbSearchResultsContainsOnlyDataSourceTypeFromQuery() {
        String query = context.get("searchQuery", String.class);
        String dataSourceType = extractStringsInQuotes(query).get(0);

        List<CBEntity> entities = context.get("searchResults", List.class);

        CBEntity wrongEntity = entities.stream()
                .filter(cbEntity -> !cbEntity.getSources().contains(dataSourceType))
                .findAny().orElse(null);

        assertNull("Search by:" + query + " return:" + toJsonString(wrongEntity), wrongEntity);
    }

    @Then("CB search results contains only subSource from query")
    public void cbSearchResultsContainsOnlyDataSubSourceFromQuery() {
        String query = context.get("searchQuery", String.class);
        String subSource = extractStringsInQuotes(query).get(0);

        List<CBEntity> entities = context.get("searchResults", List.class);

        CBEntity wrongEntity = entities.stream()
                .filter(cbEntity -> !cbEntity.getSubSourceType().contains(subSource))
                .findAny().orElse(null);

        assertNull("Search by:" + query + " return:" + toJsonString(wrongEntity), wrongEntity);
    }

    @Then("CB search results contains only recordType from query")
    public void cbSearchResultsContainsOnlyRecordTypeFromQuery() {
        String query = context.get("searchQuery", String.class);
        String recordType = extractStringsInQuotes(query).get(0);

        List<CBEntity> entities = context.get("searchResults", List.class);

        CBEntity wrongEntity = entities.stream()
                .filter(cbEntity -> !cbEntity.getRecordType().contains(recordType))
                .findAny().orElse(null);

        assertNull("Search by:" + query + " return:" + toJsonString(wrongEntity), wrongEntity);
    }

    @When("I send CB search request - eventTime:$eventTime, source:$source, objectType:$objectType, pageNumber:$pageNumber, pageSize:$pageSize")
    public void searchByEventTime(String eventTime, String source, String objectType, String pageNumber, String pageSize) {

        TimeRange timeRange = stringToTimeRange(eventTime);
        String query = timeRangeToQuery(timeRange);

        CBSearchFilter filter = new CBSearchFilter(source, objectType, query, pageSize, pageNumber);

        OperationResult<List<CBEntity>> operationResult = service.search(filter);
        List<CBEntity> searchResults = operationResult.getEntity();

        context.put("searchResults", searchResults);
        context.put("timeRange", timeRange);
        context.put("query", query);
    }

    @Then("CB search results contains only eventTime from query")
    public void cbSearchResultsContainsOnlyEventTimeFromQuery() {
        List<CBEntity> entities = context.get("searchResults", List.class);
        TimeRange timeRange = context.get("timeRange", TimeRange.class);
        String query = context.get("query", String.class);

        CBEntity wrongEntity = entities.stream()
                .filter(cbEntity -> !inRange(cbEntity.getEventTime(), timeRange))
                .findAny().orElse(null);

        assertNull("Search by:" + query + " return:" + toJsonString(wrongEntity), wrongEntity);
    }

    @When("I send CB search count request - query:$query, source:$source, objectType:$objectType")
    public void cbSearchResultCount(String query, String source, String objectType) {
        CBSearchFilter filter = new CBSearchFilter(source, objectType, query);

        OperationResult<CBSearchResult> result = service.count(filter);

        context.put("CBSearchResult", result.getEntity());
        context.put("searchQuery", query);
    }

    @Then("TotalCount in search result $criteria $size")
    public void SearchResultTolalCountShouldBe(String criteria, String size) {
        CBSearchResult cbSearchResult = context.get("CBSearchResult", CBSearchResult.class);

        int expectedCount = Integer.valueOf(size);
        boolean condition = compareByCriteria(criteria, cbSearchResult.getTotalCount(), expectedCount);
        assertTrue("Expected search results count " + criteria + " " + size + ", but was: " + cbSearchResult.getTotalCount(), condition);
    }
}
