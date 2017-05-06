Meta:
@story permissionManagement


Lifecycle:
Before:
Given I sign in as admin user


Scenario: Get list of permission
When I send get list of permission request
Then Request is successful
And Permission list size more than 0


Scenario: API.Getting list of responsibilities
When I send get list of responsibilities request
Then Request is successful
And Responsibilities list size more than 0


Scenario: API.Getting list of titles
When I send get list of titles request
Then Request is successful
And Titles list size more than 0


Scenario: API.Creation of new responsibility
When I send get list of permission request
Then Request is successful
And Permission list size more than 0

When I send create a new responsibility request
Then Request is successful
And Responsibility is correct

When I send delete responsibility request
Then Request is successful


Scenario: API.Deleting of responsibility
When I send get list of permission request
Then Request is successful
And Permission list size more than 0

When I send create a new responsibility request
Then Request is successful
And Responsibility is correct

When I send delete responsibility request
Then Request is successful

When I send get list of responsibilities request
Then Request is successful
And Responsibility is not in the list