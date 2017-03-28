Meta:
@component userManagement

Scenario: API.Create, Read, Update, Delete role
Given I sign in as admin user

When I send create a new role request
Then Request is successful
And Role is correct

When I send update role request
Then Request is successful
And Role is correct

When I send delete Role request
Then Request is successful
When I send get list of Roles request
Then Request is successful
Then Role is deleted

Scenario: API.Create, Update, Delete user group without role
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
And CreatedOrUpdated group is correct

When I send update user group request
Then Request is successful
And CreatedOrUpdated group is correct
When I send delete group request
Then Request is successful


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
Then Request is successful
When I send delete Role request
Then Request is successful
When I send get list of Roles request
Then Request is successful
Then Role is deleted


Scenario: API.Create, Update, Delete new user with group
Given I sign in as admin user
When I send create a new group without any roles
Then Request is successful
When I send create a new user with group request
Then Request is successful
And Created user is correct

When I send update user request
Then Request is successful
And Created user is correct

When I send delete user request
Then Request is successful

When I send delete group request
Then Request is successful


Scenario: Get list of user groups
Given I sign in as admin user
When I send get list of users group
Then Request is successful
And Users group list size more than 0


Scenario: Get the list of users
Given I sign in as admin user
When I send get list of users
Then Request is successful
And Users list size more than 0


Scenario: API.User as <role> is able to sign out
Given I sign in as <role> user
When I send get current user request
Then Request is successful

When I send sign out request
Then Request is successful

When I send get current user request
Then Request is unsuccessful

Examples:
| role  |
| admin |


Scenario: API.Changing password to user
Meta:
@stage
Given I sign in as admin user

When I send create a new user
Then Request is successful
When I send sign out request
Then Request is successful

When I sign in as new created user
Then Request is successful
When I send sign out request
Then Request is successful

Given I sign in as admin user

When I change user password to random
Then Request is successful
When I send sign out request
Then Request is successful

When I sign in as new created user
Then Request is successful
When I send sign out request
Then Request is successful


Scenario: Cleanup groups
Meta:
@deprecated
Given I sign in as admin user
When I send get list of users group
And I send get list of Roles request
Then delete from groups phantom roles
Then delete all groups without roles and users

Scenario: Cleanup users
Meta:
@deprecated
Given I sign in as admin user
When I send get list of users
Then Request is successful
Then delete all users without roles and groups