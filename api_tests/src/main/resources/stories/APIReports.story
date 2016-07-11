Meta:
@component records


Scenario: API.Create manual report
Meta:
@wip
Given I sign in as admin user
When Generate new report with logged user as owner
And Add new random record to report
And Add categories to report
And I send create manual report
Then I got response code 201
And Created report is correct