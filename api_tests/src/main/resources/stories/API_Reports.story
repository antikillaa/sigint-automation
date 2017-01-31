Meta:
@component reports


Scenario: API.Create manual report
Given I sign in as admin user
When Generate new report with logged user as owner
And Add new random record to report
And Add categories to report
And I send create manual report
Then Request is successful
And Created report is correct

Scenario: API.Create report category
Given I sign in as admin user
When I create new report category
Then Request is successful
And Report category is correct