Meta:
@story reports


Scenario: API.Create manual report
Given I sign in as admin user
When Generate new report with logged user as owner
And Add new random record to report
And Add categories to report
And I send create manual report
Then Request is successful
And Created report is correct