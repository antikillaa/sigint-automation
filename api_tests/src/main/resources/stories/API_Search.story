Meta:
@story search

Scenario: The maximum number of search results for a single query is limited to 1000 records
Given I sign in as root user
When I send records search request, keywords: <keywords>, pageSize <maxSize>
Then Request is successful
And Profile enlity list size more than <minSize>
And Profile enlity list size less than <maxSize>

Examples:
| keywords | minSize | maxSize |
|| 0 | 1001 |