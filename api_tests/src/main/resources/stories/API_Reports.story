Meta:
@story reports

Lifecycle:
Before:

Scenario: Create a report
Given I sign in as user with permissions FILE_VIEW, FILE_UPDATE, FILE_CREATE
When I send create finder file request
Then Request is successful

Given I sign in as admin user
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful


When I send delete a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 100 |