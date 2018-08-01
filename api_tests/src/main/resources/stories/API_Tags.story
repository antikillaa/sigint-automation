Meta:
@story tag


Lifecycle:
Before:
Given I sign in as admin user

Scenario: API.Addition of new tag
When I send create new tag request
Then Request is successful

When I send get list of tags request
Then Request is successful
And Tags list size more than 0

When I get created tag from list of tags

When I send delete tag request
Then Request is successful


Scenario: API.Getting list of tags
When I send create new tag request
Then Request is successful

When I send get list of tags request
Then Request is successful
And Tags list size more than 0

When I get created tag from list of tags

When I send delete tag request
Then Request is successful


Scenario: API.Search of tags by filters
When I send create new tag request
Then Request is successful

When I send search tags by <criteria> with <value> request
Then Request is successful
And Tags list size more than 0
And tags search result are correct

When I get created tag from list of tags

When I send delete tag request
Then Request is successful

Examples:
| criteria      | value  |
| name          | random |
| empty         |        |


Scenario: API.Deleting of tag
When I send create new tag request
Then Request is successful

When I send get list of tags request
Then Request is successful
And Tags list size more than 0

When I get created tag from list of tags

When I send delete tag request
Then Request is successful