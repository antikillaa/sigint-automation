Meta:
@story rfa

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful

Scenario: Create a RFA
Meta:
@wip
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send create a RFA request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | entity| type:"CALL" AND HAS_VPRINT:"true" |      | 0 | 1 |