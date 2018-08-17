Meta:
@story rfi

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful

Scenario: Create a RFI

When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created