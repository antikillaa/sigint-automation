Meta:
@API
@story rfi

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful

Scenario: Create/Delete a RFI
When I send generate RFI number request
Then Request is successful

When I send create a <type> RFI request
Then Request is successful
And RFI is correct

When I send view a RFI request
Then Request is successful

When I send delete a RFI request
Then Request is successful

When I send view a RFI request
Then Request is unsuccessful

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Submit for Approval a RFI
When I send generate RFI number request
Then Request is successful

When I send create a <type> RFI request
Then Request is successful
And RFI is correct

When I send view a RFI request
Then Request is successful

When I send get owner members a RFI request
Then Request is successful

When I send submit a RFI request
Then Request is successful
Then RFI is submitted

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Approve a RFI via submit button
When I send generate RFI number request
Then Request is successful

When I send create a <type> RFI request
Then Request is successful
And RFI is correct

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

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Cancel a RFI
When I send generate RFI number request
Then Request is successful

When I send create a <type> RFI request
Then Request is successful
And RFI is correct

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

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Take ownership a RFI
When I send generate RFI number request
Then Request is successful

When I send get owner teams a <type> RFI request
Then Request is successful

When I send send a RFI request
Then Request is successful

When I send view a RFI request
Then Request is successful

When I send take ownership a RFI request
Then Request is successful
Then RFI is ownershipped

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Send a RFI
When I send generate RFI number request
Then Request is successful

When I send get owner teams a <type> RFI request
Then Request is successful

When I send send a RFI request
Then Request is successful
Then RFI is sent

When I send view a RFI request
Then Request is successful

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Complete a RFI via send button
When I send generate RFI number request
Then Request is successful

When I send get owner teams a <type> RFI request
Then Request is successful

When I send send a RFI request
Then Request is successful

When I send view a RFI request
Then Request is successful

When I send take ownership a RFI request
Then Request is successful
Then RFI is ownershipped

When I send complete took ownership a RFI request
Then Request is successful
Then RFI is completed

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Unassign a RFI
When I send generate RFI number request
Then Request is successful

When I send get owner teams a <type> RFI request
Then Request is successful

When I send send a RFI request
Then Request is successful

When I send view a RFI request
Then Request is successful

When I send take ownership a RFI request
Then Request is successful
Then RFI is ownershipped

When I send unassign a RFI request
Then Request is successful
Then RFI is unassigned

Examples:
|type|
|EXTERNAL|
|INTERNAL|

Scenario: Edit a RFI
When I send generate RFI number request
Then Request is successful

When I send create a <type> RFI request
Then Request is successful
And RFI is correct

When I send view a RFI request
Then Request is successful

When I send edit a RFI request
Then Request is successful
And RFI is correct

Examples:
|type|
|EXTERNAL|
|INTERNAL|

