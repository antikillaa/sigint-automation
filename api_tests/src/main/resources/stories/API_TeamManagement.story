Meta:
@story teamManagement

Lifecycle:
Before:
Given I sign in as admin user


Scenario: Getting a list of all teams
When I send get list of teams
Then Request is successful
And Teams list size more than 0


Scenario: API.Creation of new team
When I send create a new team
Then Request is successful
And Team is correct

When I send delete team request
Then Request is successful


Scenario: User is able to delete a team
When I send create a new team
Then Request is successful
And Team is correct

When I send delete team request
Then Request is successful

When I send get list of teams
Then Request is successful
And Team is not in the list


Scenario: User is able to update a team
When I send create a new team
Then Request is successful

When I send update team request
Then Request is successful
And Team is correct

When I send delete team request
Then Request is successful


Scenario: API.Getting a team by id
When I send create a new team
Then Request is successful

When I send get team details request
Then Request is successful
And Team is correct

When I send delete team request
Then Request is successful


Scenario: User is able to search/filter users and teams
When I send create a new team
Then Request is successful

When I send search users and teams by <criteria> with <value> request
Then Request is successful
And Users and Teams list size more than 0
And Users and Teams search result are correct

When I send delete team request
Then Request is successful

When I send create a new user
Then Request is successful

When I send search users and teams by <criteria> with <value> request
Then Request is successful
And Users and Teams list size more than 0
And Users and Teams search result are correct

When I send delete user request
Then Request is successful

Examples:
| criteria      | value  |
| dataSources   | random |
| name          | random |
| orgTypes      | random |
| titles        | random |
| clearances    | random |
| orgids        | random |
| parentorgid   | random |