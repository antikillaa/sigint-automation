Scenario: API.Add new target group with target in body
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
When I send create target group with targets request
Then I got response code 200
And Created target group is correct

Scenario: API.Add new target with target groups
Meta:
@TEEL 1715
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send create target with targets group request
Then I got response code 200
And Created target is correct