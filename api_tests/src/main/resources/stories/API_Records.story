Meta:
@component records


Scenario: API.User creates manual record - SMS
Given I sign in as admin user
When I send create manual record - SMS
Then Request is successful
And Created record is correct

Scenario: API.User creates manual record - Voice
Given I sign in as admin user
When I send create manual record - Voice
Then Request is successful
And Created record is correct