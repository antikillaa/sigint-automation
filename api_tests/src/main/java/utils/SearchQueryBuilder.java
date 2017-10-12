package utils;

public class SearchQueryBuilder {

    public String recordStatusToQuery(String recordStatus) {
        String query;
        switch (recordStatus.toLowerCase()) {
            case "unassigned":
                query = RecordStatusFilterToQuery.unassigned.getQuery();
                break;
            case "unprocessed":
                query = RecordStatusFilterToQuery.unprocessed.getQuery();
                break;
            case "unprocessed unassigned":
                query = RecordStatusFilterToQuery.unprocessed_unassigned.getQuery();
                break;
            case "reported":
                query = RecordStatusFilterToQuery.reported.getQuery();
                break;
            case "reported unassigned":
                query = RecordStatusFilterToQuery.reported_unassigned.getQuery();
                break;
            case "unimportant":
                query = RecordStatusFilterToQuery.unimportant.getQuery();
                break;
            case "unimportant unassigned":
                query = RecordStatusFilterToQuery.unimportant_unassigned.getQuery();
                break;
            default:
                throw new AssertionError("Unknown recordStatus filter value: " + recordStatus);
        }
        return query;
    }
}
