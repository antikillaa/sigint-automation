Meta:
@modules UI
@phase sanity
@component records


Scenario: User is able to create a manual record (SMS TEXT)
Given I logged in as OPERATOR
And I navigate to Records -> Search page
When I open create record form
And I fill the form for SMS record
And I filter records by current date
Then I should see new record on the table


Scenario: User is able to create a manual record (CALL)
Meta:
@wip
Given I logged in as OPERATOR
And I navigate to Records -> Search page
When I open create record form
And I fill the form for Voice record
And I filter records by current date
Then I should see new record on the table


Scenario: User can create report from record processing page and save it as Draft
Given I logged in as OPERATOR
And I navigate to Records -> Search page
When I open create record form
And I fill the form for SMS record
And I filter records by current date
Then I should see new record on the table
Given I navigate to Records -> Processed page
When I checked checkbox on record with non-reported status
And I press 'Create Report' button
And fill out required fields on 'Create Report' page
And I press 'Save As Draft' button
Then report appears on 'Reports->Draft' page
And report status is 'DRAFT' on 'Reports->All' page
And record status is 'REPORTED' on 'Records->Processed' page


Scenario: User is able to attach record to existing report
Given I logged in as OPERATOR
And I navigate to Records -> Search page
When I checked checkbox on the first non-REPORTED record in table
And I press 'Create report' button against it
And I press 'Attach to report' button on 'Record Details' modal dialog
And I select report, which records should be attached to
And I press 'Save As Draft' button
Then record is attached to the report
And report status is 'DRAFT' on 'Reports->All' page


Scenario: User can create report from record processing screen and send it to Analyst for review
Given I logged in as OPERATOR
And I navigate to Records -> Processing page
When I select first record in the table
And I press 'Create Report' button
And fill out required fields on 'Create Report' page
And I press 'Submit' button
And I sign out
Then I logged in as ANALYST
And report appears on 'Reports->Ready for Review' page
And report status is 'UNASSIGNED' on 'Reports->All' page
And record status is 'REPORTED' on 'Records->Search' page

