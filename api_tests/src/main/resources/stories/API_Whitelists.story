Meta:
@story whitelist

Lifecycle:
Before:
Given I sign in as admin user
When I send create new whitelist entry request
Then Request is successful
And Whitelist entry is correct
After:
Given I sign in as admin user
When I send delete whitelist entry request
Then Request is successful


Scenario: API. Create new whitelist entry
When I send get list of whitelist entries request
Then Request is successful
Then Whitelists list size is more than 0
Then Whitelist entry is in list

Scenario: API.Get whitelist entry details by id
When I send view whitelist entry request
Then Request is successful
And Whitelist entry is correct

Scenario: API. Update whitelist entry
When I send update whitelist entry request
Then Request is successful
And Whitelist entry is correct

Scenario: API. Delete whitelist entry
When I send view whitelist entry request
Then Request is successful

Scenario: API. Get all whitelist entries
When I send get list of whitelist entries request
Then Request is successful
And Whitelists list size is more than 0

Scenario: API.Search whitelists by filters
When I send search whitelists by <criteria> with <value> request
Then Request is successful
And Whitelists list size is more than 0
And Whitelists search result is correct

Examples:
| criteria      | value  |
| identifier    | random |
| description   | random |
| type          | random |
| updatedAfter  | random |
| empty         |        |
