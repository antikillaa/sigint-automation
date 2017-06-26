Meta:
@story dicrionaries

Scenario: Get list of subSources
Given I sign in as admin user
When I send get list of dictionary subSources request
Then Request is successful
Then SubSources list size is more than 0


Scenario: Get list of sources
Given I sign in as admin user
When I send get list of dictionary sources request
Then Request is successful
Then Dictionary source list size more than 0