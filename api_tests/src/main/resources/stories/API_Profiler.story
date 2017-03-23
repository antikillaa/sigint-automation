Meta:
@component profiler


Scenario: API.(New profiler) Addition of new target group without targets
Given I sign in as admin user
When I send create target group without targets request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: (New profiler) Deleting of target group
Given I sign in as admin user
When I send create target group without targets request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: (New profiler) API.Upload targets (G4 comp.)
Given I sign in as admin user
When I send upload targets XLS file request with <targetCount> random targets
Then Request is successful
And Upload result of <targetCount> targets is successful

Examples:
| criteria     | value  | targetCount |
| updatedAfter | random | 1           |


Scenario: (New profiler) API.Search targets entries using search filters (G4 comp.)
Given I sign in as admin user
When I send upload targets XLS file request with <targetCount> random targets
Then Request is successful
And Upload result of <targetCount> targets is successful
When I get random target from targets list
And I send search targets by <criteria> and value <value>
Then targets search result are correct
And searched target in search result list

Examples:
| criteria     | value  | targetCount |
| updatedAfter | random | 1           |


Scenario: (New profiler) API.Search target groups using search filters (G4 comp.)
Given I sign in as admin user
When I send create target group without targets request
Then Request is successful
And Created target group is correct

When I send search targetGroups by <criteria> and value <value>
Then targetGroups search result are correct
And searched targetGroups in search result list

When I send delete target group request
Then Request is successful

Examples:
| criteria | value  |
| updatedAfter | random |


Scenario: (New profiler) API.Get all targets (G4 comp.)
Given I sign in as admin user
When I send get list of targets request
Then Request is successful
And Target list size more than 0


Scenario: (New profiler) API.Get all target groups (G4 comp.)
Given I sign in as admin user
When I send get list of target group request
Then Request is successful
And Target group list size more than 0


Scenario: API.(New profiler) Addition of new profile draft
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: (New profiler) Deleting of profile draft
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: (New profiler) Displaying of profile draft details
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send get profile draft details request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: API.(New profiler) Publishing of new profile draft
Meta:
@dev
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send publish profile draft request
Then Request is successful
And Profile draft is correct

When I send get profile details request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful


Scenario: (New profiler) Deleting of profile
Given I sign in as admin user
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send publish profile draft request
Then Request is successful
And Profile draft is correct

When I send delete profile request
Then Request is successful

When I send get profile details request
Then Request is unsuccessful


Scenario: API.(New profiler) Get list of all profile drafts
Given I sign in as admin user
When I send get list of profile drafts request
Then Request is successful
And Profile drafts list size more than 0