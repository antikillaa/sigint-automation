Meta:
@story whitelist


Scenario: API. Get all whitelist entries
Given I sign in as admin user
When I send create new whitelist entry request
Then Request is successful
When I send get list of whitelist entries request
Then Request is successful
And Whitelists list size more then 0

Scenario: API. Create new whitelist entry
Given I sign in as admin user
When I send create new whitelist entry request
Then Request is successful
When I send get list of whitelist entries request
Then Request is successful
And Whitelist entry is correct

Scenario: API.Get whitelist entry details by id
Given I sign in as admin user
When I send get list of whitelist entries request
Then Request is successful
And Whitelists list size more then 0
When I get random whitelist entry from list
When I send view whitelist entry request
Then Request is successful
And Whitelist entry is correct

Scenario: API. Update whitelist entry
Given I sign in as admin user
When I send create new whitelist entry request
Then Request is successful
When I send update whitelist entry request
Then Request is successful
When I send view whitelist entry request
Then Whitelist entry is correct

Scenario: API. Delete whitelist entry
Given I sign in as admin user
When I send create new whitelist entry request
Then Request is successful
When I send delete whitelist entry request
Then Request is successful
When I send get list of whitelist entries request
Then Whitelist entry is out common list




