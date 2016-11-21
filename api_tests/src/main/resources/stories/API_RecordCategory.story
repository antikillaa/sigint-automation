Meta:
@component recordCategory


Scenario: API.Create record category
Meta:
@performance
Given I sign in as admin user
When I send create new random record category request
Then I got response code 200
And Result message should be 'ok'
When I send get list of record categories request
Then I got response code 200
And Record categories list size more than 0
And Record category is in list


Scenario: API.Get list of record categories
Given I sign in as admin user
When I send get list of record categories request
Then I got response code 200
And Record categories list size more than 0


Scenario: API.View record category details
Given I sign in as admin user
When I send get list of record categories request
Then I got response code 200
And Record categories list size more than 0
When I get random record category from list
And I send view record category request
Then I got response code 200
And Record category is correct


Scenario: API.Update record category
Given I sign in as admin user
When I send get list of record categories request
Then I got response code 200
And Record categories list size more than 0
When I get random record category from list
And I send update record category request
Then I got response code 200
And Result message should be 'ok'
When I send view record category request
Then Record category is correct