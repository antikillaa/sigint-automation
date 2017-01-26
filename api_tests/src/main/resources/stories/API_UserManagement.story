Meta:
@component userManagement


Scenario: API.Create a new role
Given I sign in as admin user
When I send create a new role request
Then Request is successful
And Created role is correct
When I send delete Role request
Then Request is successful
When I send get list of Roles request
Then Request is successful
Then Role is deleted

Scenario: API.Create user group without role
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
And Created group is correct

Scenario: API.Adding user roles to user groups
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
When I send create a new role request
Then Request is successful
When I send add a created role to group request
Then Request is successful
And Updated group is correct
When I send delete group request
Then Request message should be 'success'
When I send delete Role request
Then Request is successful
When I send get list of Roles request
Then Request is successful
Then Role is deleted

Scenario: API.Create new user with group
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
When I send create a new user with group request
Then Request is successful
And Created user is correct

Scenario: Get list of user groups
Given I sign in as admin user
When I send get list of users group
Then Request is successful
And Users group list size more than 0