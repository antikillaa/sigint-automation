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
When I send upload targets request with XLS file containing 3 targets without specified id
Then I got response code 200
And Upload result of 3 targets is successful

Scenario: API.Upload new target without group and ID
Given I sign in as admin user
When I send upload targets request with XLS file containing 1 targets without specified id
Then I got response code 200
And Upload result of 1 targets is successful
When I send search targets by name and value random
Then targets search result are correct
And uploaded target in list
And target has auto-generated ID

Scenario: API.Upload updated target
Given I sign in as admin user
When I send upload targets request with XLS file containing 1 targets without specified id
Then I got response code 200
And Upload result of 1 targets is successful
When I send search targets by name and value random
Then targets search result are correct
And uploaded target in list
When I send upload updated target request
Then I got response code 200
And Upload result of 1 targets is successful
When I send search targets by name and value random
Then targets search result are correct
And uploaded target in list

Scenario: API.Upload new target with existing group
Given I sign in as admin user
When I send get list of target group request
Then I got response code 200
When I send upload targets request with XLS file containing 1 targets with existing group request
Then I got response code 200
And Upload result of 1 targets is successful
When I send search targets by name and value random
Then targets search result are correct
And uploaded target in list
When I send get list of target group request
Then existing group is listed in list only once
When I send get groups list of new target request
Then target group in list

Scenario: API.Upload new target with new group
Given I sign in as admin user
When I send upload new 1 targets with new group request
Then I got response code 200
And Upload result of 1 targets is successful
When I send search targets by name and value random
Then targets search result are correct
And uploaded target in list
When I send get list of target group request
Then target group in list
When I send get groups list of new target request
Then target group in list


Scenario: Targets generator
Meta:
@TEEL
Given generate XLS with 5000 target


Scenario: API.Generate SSMS
Meta:
@TEEL
Given I sign in as admin user
And Generate 3 SSMS
When Upload file with generated SSMS list


Scenario: API.Check matching results
Meta:
@TEEL
Given I sign in as admin user
And Generate 10 SSMS
When Upload file with generated SSMS list
When I send create target with <match_criterion> request
Then I got response code 200
And target creation result is successful
When I send upload <data_type> data request
Then I got response code 201
And data is processed
When I send get upload details request
Then I got response code 200
And search results contains targets matched with <match_criterion> and have <targetResultType>

Examples:
| data_type | match_criterion | targetResultType |
| sdata | keywords | MENTION |
| sdata | callee's phone number | HIT |
| sdata | caller's phone number | HIT |
| sdata | phone number in text | MENTION |
| sdata | target name | MENTION |
| tdata | keywords | MENTION |
| tdata | callee's phone number | HIT |
| tdata | caller's phone number | HIT |
| tdata | phone number in text | MENTION |
| tdata | target name | MENTION |