Meta:
@story profiler

Lifecycle:
Before:
Given I sign in as admin user


Scenario: API.Addition of new target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: Deleting of target group
When I send create target group request
Then Request is successful
And Created target group is correct

When I send delete target group request
Then Request is successful


Scenario: API.Addition of new profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: Deleting of profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: Displaying of profile draft details
When I send create profile draft request
Then Request is successful
And Profile draft is correct
When I send get profile draft details request
Then Request is successful
And Profile draft is correct
When I send delete profile draft request
Then Request is successful


Scenario: API.Publishing of new profile draft
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


Scenario: Deleting of profile
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


Scenario: Updating of profile draft
When I send create profile draft request
Then Request is successful
And Profile draft is correct

When I send create target group request
Then Request is successful

When I add profile draft to target group

When I send update profile request
Then Request is successful
And Profile draft is correct

When I send get profile draft details request
Then Request is successful
And Profile draft is correct

When I send delete profile draft request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: API.Get list of all profile drafts
When I send get list of profile drafts request
Then Request is successful
And Profile drafts list size more than 0


Scenario: API.Displaying of top level target groups list
When I send create target group request
Then Request is successful
When I send get list of top target groups request
Then Request is successful
Then Created target group in list

When I send delete target group request
Then Request is successful


Scenario: API.Merge two profiles into one
Meta:
@wip
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

When I send get first merged profile details request
Then Request is unsuccessful
When I send get second merged profile details request
Then Request is unsuccessful

!-- teardown test data
When I send delete profile request
Then Request is successful
When I send delete target group request
Then Request is successful
When I send delete target group request
Then Request is successful


Scenario: Addition of new child target group
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


Scenario: API.Get contents of target group
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