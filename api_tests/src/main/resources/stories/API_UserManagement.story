Meta:
@component userManagement


Scenario: API.Create a new role
Meta:
@deprecated
Given I sign in as admin user
When I send create a new role request
Then Request is successful
And Created role is correct

Scenario: API.Create user group without role
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
And Created group is correct

Scenario: API.Adding user roles to user groups
Meta:
@deprecated
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
When I send create a new role request
Then Request is successful
When I send add a created role to group request
Then Request is successful
And Updated group is correct

Scenario: API.Create new user with group
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
When I send create a new user with group request
Then Request is successful
And Created user is correct

