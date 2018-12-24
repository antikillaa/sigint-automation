Meta:
@UI
@component Search
@stage
@search


Lifecycle:
Before:
Scope: SCENARIO
Given load story ../aux-main-stories/auxSignIn.story with example table:
data/QE_login_analyst.table
After:
Scope: SCENARIO
Outcome: ANY
Given I Sign Out




Scenario: All the data sources are working in card view
Meta:@devsmoke
Given I navigate to Search
Then I should see Search page
When I enter search criteria (*) on the Search page
When I start search on the Search page
Given I setup Search Authorization

When I open Card View
Given load story ../aux-search-stories/entities/auxCheckSearchResultsEntitiesNumbers.story

Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I Apply Search using Search Filter on the Search page

When I open Card View
Given load story ../aux-search-stories/events/auxCheckSearchResultsEventsNumbers.story


Examples:
|SIGINT_events|EID_events|GOVINT_events|OSINT_events|CIO_events|FININT_events|TRAFFIC_events|Profiler_entities|Documents_entities|SIGINT_entities|EID_entities|GOVINT_entities|OSINT_entities|CIO_entities|
|1         |1         |1          |1        |1         |1            |1             |1                |1                 |1            |1         |1            |1           |1       |



Scenario: Verfiy  data source filters are working
Given I navigate to Search
Given I open Search Filter on the Search page
Given I set Source Type to SIGINT on the Search Filter page
Given I set period range Last 90 days as Event Time Period on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to EID on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to GOVINT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view


Given I open Search Filter on the Search page
Given I set Source Type to FININT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to Traffic on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view

Given I open Search Filter on the Search page
Given I set Source Type to OSINT on the Search Filter page
Given I Apply Search using Search Filter on the Search page
Given I setup Search Authorization
Then I should see at least 1 search result(s) on the current view





