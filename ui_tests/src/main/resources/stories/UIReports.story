Meta:
@modules UI
@phase sanity
@component reports


Scenario: User is able to create a manual report and save it as Draft
Given I logged in as Operator
And I'm on 'Reports->All' page
When I press 'Create Manual Report' button
And I fill out required fields on 'Create Manual Report' page
And I press 'Save As Draft' button
Then report appears on 'Reports->Draft' page
And report status is 'Draft' on 'Reports->All' page