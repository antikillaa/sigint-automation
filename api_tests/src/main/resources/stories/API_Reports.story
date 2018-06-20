Meta:
@story reports

Lifecycle:
Before:
Given I sign in as admin user

Scenario: Create a report
Meta: @wip
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 10 |
When I send send generate report number request
Then Request is successful
When I send create a report request