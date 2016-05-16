Scenario: API.Tasker can create new RFI with all fields
Given I sign in as tasker user
When I send create RFI request
Then I got response code 200
And Created rfi is correct

Scenario: API.Tasker can update existing RFI
Given I sign in as tasker user
When I send create RFI request
Then I got response code 200
When I update created RFI
Then I got response code 200
And RFI is updated


Scenario: API.Tasker can find RFI using search filters
Given I sign in as tasker user
When I send create RFI request
Then I got response code 200
When I search RFI by <criteria>
Then Search results are correct

Examples:
|criteria|
|state|
|min priority|
|created date|
|due date|
|created by|


Scenario: API.Tasker can get details of RFI
Meta:
@wip
Given I sign in as tasker user
When I send create RFI request
Then I got response code 200
When I get details of created RFI
Then RFI details get via details are correct