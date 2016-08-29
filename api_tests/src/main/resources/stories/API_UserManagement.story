Meta:
@component userManagement


Scenario: API.Create a new role
Given I sign in as admin user
When I send create a new role request
Then I got response code 200
And Created role is correct

Scenario: API.Create user group without role
Given I sign in as admin user
When I send create a new group without any roles
Then I got response code 200
And Created group is correct

Scenario: API.Adding user roles to user groups
Given I sign in as admin user
When I send create a new group without any roles
Then I got response code 200
When I send create a new role request
Then I got response code 200
When I send add a created role to group request
Then I got response code 200
And Updated group is correct

Scenario: API.Create new user with group
Given I sign in as admin user
When I send create a new group without any roles
Then I got response code 200
When I send create a new user with group request
Then I got response code 200
And Created user is correct

