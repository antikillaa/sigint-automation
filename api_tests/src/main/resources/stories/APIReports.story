Meta:
@component records


Scenario: API.Create manual report
Meta:
@wip
Given I sign in as admin user
When I send create manual report
Then I got response code 201
And Created report is correct