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

Scenario: API. Import whitelists
Given I generate 5 random whitelists
And I write whitelists to CSV without header
When I send import whitelists request
Then Request is successful
And 5 whitelists are imported
And I delete whitelists

Scenario: API. Import whitelists with errors in file
Given I generate 1 random whitelists
And I write whitelists to CSV with header
When I send import whitelists request
Then Request is successful
And Message contains "Type is not set"
And 1 whitelists are imported
And I delete whitelists

Scenario: API. Import whitelists with duplicate values
Given I generate 1 random whitelists
And I write whitelists to CSV without header
When I send import whitelists request
Then Request is successful
And 1 whitelists are imported
When I send import whitelists request
Then Request is successful
And 0 whitelists are imported
And Message contains "already exists"

Scenario: Import non .csv file for whitelist records
Given T - SMS data file with 10 records was generated
When I send import whitelists request
Then I got response code 400
And Message contains "Unable to import non csv files"