Meta:
@story tag


Lifecycle:
Before:
Given I sign in as admin user


Scenario: Get list of permission
When I send get list of permission request
Then Request is successful
And Permission list size more than 0