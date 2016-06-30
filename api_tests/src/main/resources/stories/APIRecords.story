Meta:
@component records


Scenario: API.User creates manual record - SMS
Given I sign in as admin user
When I send create manual record - SMS
Then I got response code 200
And Created record is correct

Scenario: API.User creates manual record - Voice
Given I sign in as admin user
When I send create manual record - Voice
Then I got response code 200
And Created record is correct