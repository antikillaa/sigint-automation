Meta:
@component source


Scenario: API.Create Source
Given I sign in as admin user
When I send create new random Source request
Then I got response code 200
And Source is correct
When I send get list of sources request
Then I got response code 200
And Source list size more than 0
And Source in list
When I send delete Source request
Then I got response code 200


Scenario: API.Get list of sources
Given I sign in as admin user
When I send get list of sources request
Then I got response code 200
And Source list size more than 0


Scenario: API.View source details
Given I sign in as admin user
When I send get list of sources request
Then I got response code 200
And Source list size more than 0
Given Get random source from list
When I send view source request
Then I got response code 200
And Source is correct


Scenario: API.Update source
Given I sign in as admin user
When I send get list of sources request
Then I got response code 200
And Source list size more than 0
Given Get random source from list
When I send update source request
Then I got response code 200
And Result message should be 'ok'
When I send view source request
Then Source is correct


Scenario: API.Delete source
Given I sign in as admin user
When I send create new random Source request
Then I got response code 200
And Source is correct
When I send delete Source request
Then I got response code 200
And Result message should be 'deleted'
When I send view source request
Then Source is deleted