Meta:
@modules API
@component phonebook


Scenario: List of phonebooks - GET
Meta:
@wip
@TEEL
Given I sign in as admin user
When I send search phonebooks list with page 0 pagesize 100 request
Then I got response code 200


Scenario: API.Create PhoneBook with all fields - POST
Given I sign in as admin user
When I send create Phonebook Entry request with all fields
Then I got response code 200