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

Scenario: API.Deleting Target - DELETE
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
When I send delete target request
Then I got response code 200
And Target deleted correctly

Scenario: API.View target group details
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send get target group details request
Then Viewed target group is correct

Scenario: API.List of target groups - GET
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send get list of target group request
Then Created target group in list

Scenario: API.Update target group
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send update target group request
Then I got response code 200
And Target group updated correctly

Scenario: API.Delete target group
Given I sign in as admin user
When I send create target group without targets request
Then I got response code 200
When I send delete target group request
Then I got response code 200
And Target group deleted correctly

Scenario: API.Search targets entries using search filters
Given I sign in as admin user
When I send create target without targets group request
Then I got response code 200
And Created target is correct
When I send search targets by <criteria> and value <value>
Then targets search result are correct
And searched target entry in list

Examples:
| criteria | value |
| type | random |
| name | random |
| keywords | random |
| description | random |
| languages | random |
| phones | random |
| updatedAfter | random |


Scenario: API.Upload multiple targets
Given I sign in as admin user
When I send upload targets request with XLS file containing 3 targets
Then I got response code 200
And Upload result of 3 targets is successful