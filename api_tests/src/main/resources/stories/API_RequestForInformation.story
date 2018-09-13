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

When I send get owner members a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

Scenario: Approve a RFI
When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created

When I send view a RFI request
Then Request is successful

When I send get owner members a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

When I send Approve a RFI request
Then Request is successful
Then RFI is approved

Scenario: Cancel a RFI
When I send generate RFI number request
Then Request is successful

When I send create a RFI request
Then Request is successful
And RFI is created

When I send view a RFI request
Then Request is successful

When I send get owner members a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

When I send cancel a RFI request
Then Request is successful
Then RFI is cancelled

Scenario: Send a RFI
When I send generate RFI number request
Then Request is successful

When I send get owner teams a RFI request
Then Request is successful

When I send send a RFI request
Then Request is successful

When I send view a RFI request
Then Request is successful


Scenario: Reject a RFI

Scenario: Edit a RFI
