Meta:
@story dictionaries


Scenario: API.Dictionary. Get list of sources
Given I sign in as admin user
When I send get list of dictionary sources request
Then Request is successful
Then Dictionary source list size more than 0