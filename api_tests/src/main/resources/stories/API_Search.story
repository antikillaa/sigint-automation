Narrative:
TODO
GOVINT - Fuzzy, Wildcard
PROFILER - Fuzzy, Wildcard
entity - add Wildcard, Fuzzy scenarios

Meta:
@story search

Lifecycle:
Before:
Given I sign in as admin user

Scenario: SIGINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| SIGINT | event |       | 0 | NULL |
| SIGINT | event |       | 0 | 0    |
| SIGINT | event |       | 0 | 1100 |
| SIGINT | entity |      | 0 | NULL |
| SIGINT | entity |      | 0 | 0    |
| SIGINT | entity |      | 0 | 1100 |


Scenario: OSINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| OSINT	 | event |       | 0 | NULL |
| OSINT	 | event |       | 0 | 0    |
| OSINT  | event |       | 0 | 1100 |
| OSINT	 | entity |      | 0 | NULL |
| OSINT	 | entity |      | 0 | 0    |
| OSINT  | entity |      | 0 | 1100 |


Scenario: GOVINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| GOVINT | event |       | 0 | NULL |
| GOVINT | event |       | 0 | 0    |
| GOVINT | event |       | 0 | 1100 |
| GOVINT | entity |      | 0 | NULL |
| GOVINT | entity |      | 0 | 0    |
| GOVINT | entity |      | 0 | 1100 |


Scenario: CIO. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| CIO    | event |       | 0 | NULL |
| CIO    | event |       | 0 | 0    |
| CIO    | event |       | 0 | 1100 |
| CIO    | entity |      | 0 | NULL |
| CIO    | entity |      | 0 | 0    |
| CIO    | entity |      | 0 | 1100 |


Scenario: PROFILER. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| PROFILER | event |     | 0 | NULL |
| PROFILER | event |     | 0 | 0    |
| PROFILER | event |     | 0 | 1100 |
| PROFILER | entity |    | 0 | NULL |
| PROFILER | entity |    | 0 | 0    |
| PROFILER | entity |    | 0 | 1100 |


Scenario: SIGINT. Wild Card Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| SIGINT | event | knowledge | 0 | 100 |
| SIGINT | event | kno?le?ge | 0 | 100 |
| SIGINT | event | *knowledge   | 0 | 100 |
| SIGINT | event | *knowle?ge  | 0 | 100 |


Scenario: OSINT. Wild Card Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| OSINT | event | knowledge | 0 | 100 |
| OSINT | event | kno?le?ge | 0 | 100 |
| OSINT | event | *knowledge   | 0 | 100 |
| OSINT | event | *knowle?ge  | 0 | 100 |


Scenario: CIO. Wild Card Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| CIO | event | knowledge | 0 | 100 |
| CIO | event | kno?le?ge | 0 | 100 |
| CIO | event | *knowledge   | 0 | 100 |
| CIO | event | *knowle?ge  | 0 | 100 |


Scenario: SIGINT. Fuzzy Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| SIGINT | event | knoeledge~1 | 0 | 100 |
| SIGINT | event | knowledge~2  | 0 | 100 |
| SIGINT | event | knwldg~3 | 0 | 100 |
| SIGINT | event | nowledge~  | 0 | 100 |


Scenario: OSINT. Fuzzy Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| OSINT | event | knoeledge~1 | 0 | 100 |
| OSINT | event | knowledge~2  | 0 | 100 |
| OSINT | event | knwldg~3 | 0 | 100 |
| OSINT | event | nowledge~  | 0 | 100 |


Scenario: CIO. Fuzzy Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search contains results for query

Examples:
| source | objectType | query | pageNumber | pageSize |
| SIGINT | event | knoeledge~1 | 0 | 100 |
| SIGINT | event | knowledge~2  | 0 | 100 |
| SIGINT | event | knwldg~3 | 0 | 100 |
| SIGINT | event | nowledge~  | 0 | 100 |