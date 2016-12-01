Meta:
@component source


Scenario: API.Create Source
Given I sign in as admin user
When I send create new random Source request
Then Request is successful
And Source is correct
When I send get list of sources request
Then Request is successful
And Source list size more than 0
And Source in list


Scenario: API.Get list of sources
Given I sign in as admin user
When I send get list of sources request
Then Request is successful
And Source list size more than 0


Scenario: API.View source details
Given I sign in as admin user
When I send get list of sources request
Then Request is successful
And Source list size more than 0
Given Get random source from list
When I send view source request
Then Request is successful
And Source is correct


Scenario: API.Update source
Given I sign in as admin user
When I send get list of sources request
Then Request is successful
And Source list size more than 0
Given Get random source from list
When I send update source request
Then Request is successful
When I send view source request
Then Source is correct


Scenario: API.Delete source
Given I sign in as admin user
When I send create new random Source request
Then Request is successful
And Source is correct
When I send delete Source request
Then Request is successful
When I send view source request
Then Source is deleted