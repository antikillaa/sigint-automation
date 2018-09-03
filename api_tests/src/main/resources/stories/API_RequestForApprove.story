Meta:
@story rfa

Scenario: Create a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | entity| type:"CALL" AND HAS_VPRINT:"true" |      | 0 | 150 |