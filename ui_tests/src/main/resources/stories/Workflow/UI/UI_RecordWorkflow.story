Meta: @recordworkflow

Scenario: Verify that a manager can assign records to another user using ad-hoc assignment

Given load story ../../aux-main-stories/auxSignIn.story with example table:
|login         |password         |
|<ManagerLogin>|<ManagerPassword>|

Given I navigate to Search

Given I navigate to Search
When I enter search criteria (<SIGINTEventIMSI>) on the Search page
Given I open Search Filter on the Search page
Given load story ../../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization

When I open Card View
When I select all SIGINT event cards having identifiers: |<SIGINTEventPhone>|
When I assign selected items to (<AnalystLogin>)

Given I Sign Out

Given load story ../../aux-main-stories/auxSignIn.story with example table:
|login         |password         |
|<AnalystLogin>|<AnalystPassword>|

!-- Given I open Alerting History page

!-- Then I should see assign of (1) record(s) by (<ManagerLogin>) at row (1) of (<AssignDate>) To Do List entry on the Alerting History page

Given I navigate to My Records

When I open Card View

Then I should see at least 1 of SIGINT events on current view

When I select all SIGINT event cards having identifiers: |<SIGINTEventPhone>|

When I unassign selected items

Examples:
|ManagerLogin     |ManagerPassword|AnalystLogin     |AnalystPassword  |SIGINTEventIMSI|SIGINTEventPhone|GOVINTEventCarrier|AssignDate                                  |
|QE_UIAuto_Manager|2020@Pegasus!! |QE_UIAuto_Analyst|<ManagerPassword>|983528369265302  |9812098534            |American Airlines |{current_day}/{current_month}/{current_year}|



Scenario: Verify that a User can select records from team Record screen and take ownership on them
Given load story ../../aux-main-stories/auxSignIn.story with example table:
|login         |password         |
|<ManagerLogin>|<ManagerPassword>|

Given I navigate to OrgUnit Records
Given I setup Search Authorization
Given I open Search Filter on the Team Records page
Given I set date (01/01/2016) as Earliest Event Time on the Search Filter page
Given I Apply Search using Search Filter on the Team Records page

When I open Card View
When I open details of 1-st displayed SIGINT Event
Then I should not see Owner field
Given I close search result Details page
When I select 1-st displayed SIGINT event card
When I take ownership on selected items


Given I navigate to My Records
When I enter search criteria (<SIGINTEventPhone>) on the My Records page
When I open Card View
Then I should see 1 SIGINT event(s) on current view
When I open details of 1-st displayed SIGINT Event
Then I should see Owner field's value (<ManagerLogin>) on the search result Details page
Given I close search result Details page
When I select 1-st displayed SIGINT event card
When I unassign selected items

Examples:
|ManagerLogin     |ManagerPassword|SIGINTEventPhone|
|QE_UIAuto_Manager|2020@Pegasus!! |9812098534|
