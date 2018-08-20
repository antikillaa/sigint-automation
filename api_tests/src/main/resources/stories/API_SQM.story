Meta:
@story search

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal


Lifecycle:
Before:
Given I sign in as admin user

Scenario: SQM basic events search
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
When SQM search completed
When I get search queue results:
| eventFeed | objectType | pageNumber | pageSize |
| SIGINT | event | 0 | 20 |
| OSINT | event | 0 | 20 |
| GOVINT | event | 0 | 20 |
| GOVINT2 | event | 0 | 20 |
Then TotalCount's in search results > 0

Examples:
| sourceTypes | objectType | query  | pageNumber | pageSize |
| SIGINT, OSINT, GOVINT, GOVINT2 | event | | 0 | 20 |


Scenario: SQM basic entities search
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
When SQM search completed
When I get search queue results:
| eventFeed | objectType | pageNumber | pageSize |
| SIGINT | entity | 0 | 20 |
| OSINT | entity | 0 | 20 |
| GOVINT | entity | 0 | 20 |
| GOVINT2 | entity | 0 | 20 |
| PROFILER | entity | 0 | 20 |
| INFORMATION_MANAGEMENT | entity | 0 | 20 |
Then TotalCount's in search results > 0

Examples:
| sourceTypes | objectType | query  | pageNumber | pageSize |
| SIGINT, OSINT, GOVINT, GOVINT2, PROFILER, INFORMATION_MANAGEMENT | entity | | 0 | 20 |


Scenario: Event. The maximum number of search results for a single query is limited to 1000 records.
Meta: @nightly
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
When SQM search completed
When I get search queue results:
| eventFeed | objectType | pageNumber | pageSize |
| SIGINT | event | 0 | 1100 |
| OSINT | event | 0 | 1100 |
| GOVINT | event | 0 | 1100 |
| GOVINT2 | event | 0 | 1100 |
Then TotalCount's in search results > 0
And TotalCount's in search results < 1001

Examples:
| sourceTypes | objectType | query  | pageNumber | pageSize |
| SIGINT, OSINT, GOVINT, GOVINT2 | event | | 0 | 1100 |


Scenario: Entity. The maximum number of search results for a single query is limited to 1000 records
Meta: @nightly
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
When SQM search completed
When I get search queue results:
| eventFeed | objectType | pageNumber | pageSize |
| SIGINT | entity | 0 | 1100 |
| OSINT | entity | 0 | 1100 |
| GOVINT | entity | 0 | 1100 |
| GOVINT2 | entity | 0 | 1100 |
| PROFILER | entity | 0 | 1100 |
| INFORMATION_MANAGEMENT | entity | 0 | 1100 |
Then TotalCount's in search results > 0
And TotalCount's in search results < 1001

Examples:
| sourceTypes | objectType | query  | pageNumber | pageSize |
| SIGINT, OSINT, GOVINT, GOVINT2, PROFILER, INFORMATION_MANAGEMENT | entity | | 0 | 1100 |