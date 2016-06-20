Meta:
@component targetManagement


Scenario: API.Add new target group with target in body
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
When I send create target group with targets request
Then I got response code 200
And Created target group is correct

Scenario: API.Add new target with target groups
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send create target with targets group request
Then I got response code 200
And Created target is correct

Scenario: API.View target details
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
When I send get target details request
Then Viewed target is correct

Scenario: API.Update target entry
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
When I send update target request
Then I got response code 200
And Target updated correctly