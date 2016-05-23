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


Scenario: User is able to remove ownership from it’s own draft report
Given I logged in as Operator
And I'm on 'Reports->Draft' page
When I select first report in the table
And I press 'Edit Report' button against it
Then I should see 'Remove Ownership' button on 'Edit Report' page
When I press 'Remove Ownership' button on 'Edit Report' page
Then I should see 'Reports->Draft' page
And I should not see selected report on the reports table
When I navigate to 'Reports->Ready for Review' page
Then I should see selected report on the reports table
When I navigate to 'Reports->All' page
Then report status is 'UNASSIGNED'
And report owner should be 'Empty'


Scenario: Operator should be able to take ownership on an unassigned draft report in the “Ready for Review”
Given I logged in as Operator
And I'm on 'Reports->All' page
When I select a report in 'Unassigned' status and owner is 'Empty'
And I press 'Edit Report' button against it
And I press 'Take Ownership' button on 'Edit Report' page
Then required fields are enabled on 'Edit Report' page
And required buttons are enabled on 'Edit Report' page