Meta:
@story search

Lifecycle:
Before:
Given I sign in as admin user

Scenario: API.The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And Profile entity list size less than 1001

Examples:
| source | objectType | query  | pageNumber | pageSize |
| SIGINT | event |       | 0 | NULL |
| SIGINT | event |       | 0 | 0    |
| SIGINT | event |       | 0 | 1100 |
| SIGINT | entity |      | 0 | NULL |
| SIGINT | entity |      | 0 | 0    |
| SIGINT | entity |      | 0 | 1100 |
| OSINT	 | event |       | 0 | NULL |
| OSINT	 | event |       | 0 | 0    |
| OSINT  | event |       | 0 | 1100 |
| OSINT	 | entity |      | 0 | NULL |
| OSINT	 | entity |      | 0 | 0    |
| OSINT  | entity |      | 0 | 1100 |
| CIO    | event |       | 0 | NULL |
| CIO    | event |       | 0 | 0    |
| CIO    | event |       | 0 | 1100 |
| CIO    | entity |      | 0 | NULL |
| CIO    | entity |      | 0 | 0    |
| CIO    | entity |      | 0 | 1100 |
| GOVINT | event |       | 0 | NULL |
| GOVINT | event |       | 0 | 0    |
| GOVINT | event |       | 0 | 1100 |
| GOVINT | entity |      | 0 | NULL |
| GOVINT | entity |      | 0 | 0    |
| GOVINT | entity |      | 0 | 1100 |
| PROFILER | event |     | 0 | NULL |
| PROFILER | event |     | 0 | 0    |
| PROFILER | event |     | 0 | 1100 |
| PROFILER | entity |    | 0 | NULL |
| PROFILER | entity |    | 0 | 0    |
| PROFILER | entity |    | 0 | 1100 |


Scenario: Wild Card Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search results are correct

Examples:
| source | objectType | query | pageNumber | pageSize |
| SIGINT | event | quantity | 0 | NULL |
| SIGINT | event | quantity\? | 0 | NULL |
| SIGINT | event | qu?ntit? | 0 | NULL |
| SIGINT | event | qua*ty   | 0 | NULL |
| SIGINT | event | q?an*ty  | 0 | NULL |
| CIO | event | Qatar | 0 | NULL |
| CIO | event | Qatar\? | 0 | NULL |
| CIO | event | Q?ta? | 0 | NULL |
| CIO | event | Qa*r  | 0 | NULL |
| CIO | event | Qa?a* | 0 | NULL |


Scenario: Fuzzy Search
When I send CB search request - query:<query>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search results are correct

Examples:
| source | objectType | query | pageNumber | pageSize |
| SIGINT | event | quantuty~1 | 0 | NULL |
| SIGINT | event | quamtty~2  | 0 | NULL |
| SIGINT | event | uqantuty~3 | 0 | NULL |
| SIGINT | event | uqantuty~  | 0 | NULL |