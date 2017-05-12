Meta:
@story profiler

Lifecycle:
Before:
Given I sign in as admin user


Scenario: API.(New profiler) Addition of new target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: (New profiler) Deleting of target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: (New profiler) API.Upload targets (G4 comp.)
Meta:
@deprecated
When I send upload targets XLS file request with <targetCount> random targets
Then Request is successful
And Upload result of <targetCount> targets is successful

Examples:
| criteria     | value  | targetCount |
| updatedAfter | random | 1           |


Scenario: (New profiler) API.Search targets entries using search filters (G4 comp.)
Meta:
@deprecated
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
Meta:
@deprecated
When I send create target group request
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
Meta:
@deprecated
When I send get list of targets request
Then Request is successful
And Target list size more than 0


Scenario: (New profiler) API.Get all target groups (G4 comp.)
Meta:
@deprecated
When I send get list of target group request
Then Request is successful
And Target group list size more than 0


Scenario: API.(New profiler) Addition of new profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: (New profiler) Deleting of profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: (New profiler) Displaying of profile draft details
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send get profile draft details request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: API.(New profiler) Publishing of new profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send create target group request
Then Request is successful
And Created target group is correct

When I add profile draft to target group

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: (New profiler) Deleting of profile
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send create target group request
Then Request is successful
And Created target group is correct

When I add profile draft to target group

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: (New profiler) Updating of profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send create target group request
Then Request is successful

When I add profile draft to target group

When I send update profile request
Then Request is successful
And Profile draft is correct

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: API.(New profiler) Get list of all profile drafts
When I send get list of profile drafts request
Then Request is successful
And Profile drafts list size more than 0


Scenario: API.(New profiler) Displaying of top level target groups list
When I send create target group request
Then Request is successful
When I send get list of top target groups request
Then Request is successful
Then Created target group in list

When I send delete target group request
Then Request is successful


Scenario: API.Merge two profiles into one
When I send create profile draft request
Then Request is successful
When I send create target group request
Then Request is successful
When I add profile draft to target group
When I send publish profile draft request
Then Request is successful

When I send create profile draft request
Then Request is successful
When I send create target group request
Then Request is successful
When I add profile draft to target group
When I send publish profile draft request
Then Request is successful

When I send merge two profile into one request
Then Request is successful

When I send get profile draft details request
Then Request is successful
And Merged profile draft is correct

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct


Scenario: (New profiler) Addition of new child target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send create new child target group request
Then Request is successful

When I send get target group details request
Then Request is successful
Then created nested target group is correct

When I send get content of parent target group
Then Request is successful
Then Target group content contains created nested group

When I send delete target group request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: API.(New profiler) Get contents of target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I add profile draft to target group

When I send publish profile draft request
Then Request is successful

When I send get profile details request
Then Request is successful
And Profile is correct

When I send create new child target group request
Then Request is successful

When I send get target group details request
Then Request is successful
Then created nested target group is correct

When I send get content of parent target group
Then Request is successful
Then Target group content contains created nested group
And Target group content contains created profile

When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful
When I send delete target group request
Then Request is successful