Meta:
@story search

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: SQM basic events search
Given I sign in as admin user
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
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
Given I sign in as admin user
When I send SQM search request - query:<query>, sourceTypes:<sourceTypes>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
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