Meta:
@component userManagement


Scenario: API.Create a new role
Given I sign in as admin user
When I send create a new role request
Then I got response code 200
And Created role is correct