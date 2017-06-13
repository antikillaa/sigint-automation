Meta:
@story designation-mappings

Lifecycle:
Before:
Given I sign in as admin user
When I send create new random designation-mapping request
Then Request is successful
And Designation-mapping is correct
After:
Given I sign in as admin user
When I send delete designation-mapping request
Then Request is successful

Scenario: API.Create designation-mapping
When I send get list of designation-mappings request
Then Request is successful
Then Designation-mappings list size is more than 0
Then Designation-mapping is in list

Scenario: API.Get designation-mapping by id
When I send view designation-mapping request
Then Request is successful
And Designation-mapping is correct

Scenario: API.Update designation-mapping
When I send update designation-mapping request
Then Request is successful
And Designation-mapping is correct

Scenario: API.Delete designation-mapping
When I send view designation-mapping request
Then Request is successful

Scenario: API.Get designation-mappings list
When I send get list of designation-mappings request
Then Request is successful
And Designation-mappings list size is more than 0

Scenario: API.Search designation-mappings by filters
When I send search designation-mappings by <criteria> with <value> request
Then Request is successful
And Designation-mappings list size is more than 0
And Designation-mappings search result is correct

Examples:
| criteria      | value  |
| identifier    | random |
| type          | random |
| designation   | random |
| updatedAfter  | random |
| empty         |        |