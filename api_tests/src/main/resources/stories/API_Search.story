Meta:
@story search

Lifecycle:
Before:
Given I sign in as admin user

Scenario: API.The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, source:<source>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And Profile entity list size less than 1001

Examples:
| source | query | pageNumber | pageSize |
| SIGINT |       | 0 | NULL |
| SIGINT |       | 0 | 0    |
| SIGINT |       | 0 | 1000 |
| SIGINT |       | 0 | 1100 |
| OSINT	 |       | 0 | NULL |
| OSINT	 |       | 0 | 0    |
| OSINT	 |       | 0 | 1000 |
| OSINT  |       | 0 | 1100 |
| CIO    |       | 0 | NULL |
| CIO    |       | 0 | 0    |
| CIO    |       | 0 | 1000 |
| CIO    |       | 0 | 1100 |
| GOVINT |       | 0 | NULL |
| GOVINT |       | 0 | 0    |
| GOVINT |       | 0 | 1000 |
| GOVINT |       | 0 | 1100 |
| PROFILER |     | 0 | NULL |
| PROFILER |     | 0 | 0    |
| PROFILER |     | 0 | 1000 |
| PROFILER |     | 0 | 1100 |


Scenario: CB Search
When I send CB search request - query:<query>, source:<source>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
Then CB search results are correct

Examples:
| source | query    | pageNumber | pageSize |
| SIGINT |          | 0 | NULL |
| SIGINT | physical | 0 | NULL |
| SIGINT | p?ys?cal | 0 | NULL |
| SIGINT | ph*cal   | 0 | NULL |
| CIO |       | 0 | NULL |
| CIO | Qatar | 0 | NULL |
| CIO | Q?ta? | 0 | NULL |
| CIO | Qa*r  | 0 | NULL |
