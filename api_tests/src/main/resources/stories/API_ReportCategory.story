Meta:
@component reportCategory

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: API.Create report category
Given I sign in as admin user
When I create new report category
Then Request is successful
And Report category is correct

Scenario: API.Get list of report categories
Given I sign in as admin user
When I send get list of report categories request
Then Request is successful
And Report categories list size more than 0