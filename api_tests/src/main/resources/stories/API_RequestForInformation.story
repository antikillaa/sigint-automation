Meta:
@story rfi

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful

Scenario: Create/Delete a RFI
When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created

When I send view a RFI request
Then Request is successful

When I send delete a RFI request
Then Request is successful

When I send view a RFI request
Then Request is unsuccessful

Scenario: Submit a RFI
When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created

When I send view a RFI request
Then Request is successful

When I send get owners a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

Scenario: Approve a RFI
Meta:
@wip
When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created

When I send view a RFI request
Then Request is successful

When I send get owners a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

When I Approve a RFI request
Then Request is successful
Then RFI is approved



Scenario: Cancel a RFI

Scenario: Reject a RFI

Scenario: Edit a RFI
