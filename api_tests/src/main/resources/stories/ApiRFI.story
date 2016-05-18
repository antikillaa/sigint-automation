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
And I put RFI to search query
Then I got response code 200
When I search RFI by <criteria> and value <value>
Then Search results are correct
And Searched RFI in list

Examples:
|criteria| value |
|state| random |
|min priority| random |
|created date| random |
|due date|  random
|created by|  random |


Scenario: API.Tasker can get details of RFI
Given I sign in as tasker user
When I send create RFI request
Then I got response code 200
When I get details of created RFI
Then RFI details get via details are correct


Scenario: API.Tasker can delete RFI in status Draft
Given I sign in as tasker user
When I create new RFI in status draft
And I put RFI to search query
And I delete created RFI
Then I got response code 200
When I search RFI by state and value DRAFT
Then Search results are correct
And Searched RFI not in list

Scenario: API.Tasker can cancel RFI
Meta:
@TEEL 1640
Given I sign in as tasker user
When I send create RFI request
And I put RFI to search query
And I cancel RFI
Then I got response code 200
When I search RFI by status and value Cancelled
Then Searched RFI in list