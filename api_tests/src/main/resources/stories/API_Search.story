Meta:
@story search


Scenario: API.The maximum number of search results for a single query is limited to 1000 records
Given I sign in as admin user
When I send CB search request - sourceCategory:<source>, query:<query>, pageSize:<pageSize>
Then Request is successful
And Profile entity list size less than <maxPageSize>

Examples:
| source | query | pageSize | maxPageSize |
| SIGINT |       | NULL     | 1001 |
| SIGINT |       | 0        | 1001 |
| SIGINT |       | 1000     | 1001 |
| SIGINT |       | 1100     | 1001 |
| OSINT	 |       | NULL     | 1001 |
| OSINT	 |       | 0        | 1001 |
| OSINT	 |       | 1000     | 1001 |
| OSINT  |       | 1100     | 1001 |
| CIO    |       | NULL     | 1001 |
| CIO    |       | 0        | 1001 |
| CIO    |       | 1000     | 1001 |
| CIO    |       | 1100     | 1001 |
| GOVINT |       | NULL     | 1001 |
| GOVINT |       | 0        | 1001 |
| GOVINT |       | 1000     | 1001 |
| GOVINT |       | 1100     | 1001 |
| PROFILER |       | NULL     | 1001 |
| PROFILER |       | 0        | 1001 |
| PROFILER |       | 1000     | 1001 |
| PROFILER |       | 1100     | 1001 |