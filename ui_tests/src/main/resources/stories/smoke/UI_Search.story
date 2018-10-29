Meta:
@wip
@component smoke-test
@stage
@search


Lifecycle:
Before:
Scope: SCENARIO
Given load story ../aux-main-stories/auxSignIn.story
After:
Scope: SCENARIO
Outcome: ANY
Given I Sign Out

Scenario: All the data sources are working
Meta: @search1
Given I navigate to Search
Then I should see Search page
When I enter search criteria (*) on the Search page
When I start search on the Search page
Given I setup Search Authorization

When I open Card View
Given load story aux-search-stories/entities/auxCheckSearchResultsEntitiesNumbers.story

When I open Grid View
Given load story aux-search-stories/entities/auxCheckSearchResultsEntitiesNumbers.story

When I open Map View
Given load story aux-search-stories/entities/auxCheckSearchResultsEntitiesNumbers.story

Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I Apply Search using Search Filter on the Search page

When I open Card View
Given load story aux-search-stories/events/auxCheckSearchResultsEventsNumbers.story

When I open Grid View
Given load story aux-search-stories/events/auxCheckSearchResultsEventsNumbers.story

When I open Map View
Given load story aux-search-stories/events/auxCheckSearchResultsEventsNumbers.story


Examples:
|SIGINT_events|EID_events|GOVINT_events|OSINT_events|CIO_events|FININT_events|TRAFFIC_events|Profiler_entities|Documents_entities|SIGINT_entities|EID_entities|GOVINT_entities|OSINT_entities|CIO_entities|
|1000         |1         |400          |1000        |0         |0            |0             |1                |1                 |100            |100         |100            |100           |0       |

Scenario: User is able to scroll in card view
Given I navigate to Search
Then I should see Search page
When I enter search criteria (*) on the Search page
When I start search on the Search page
Given I setup Search Authorization

When I open Card View

Given load story aux-search-stories/entities/auxCheckSearchResulsEntitiesVerticalScroll.story

Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I Apply Search using Search Filter on the Search page

Given load story aux-search-stories/events/auxCheckSearchResulsEventsVerticalScroll.story


Examples:
|SIGINT_events|EID_events|GOVINT_events|OSINT_events|CIO_events|Profiler_entities|Documents_entities|SIGINT_entities|EID_entities|GOVINT_entities|OSINT_entities|CIO_entities|
|1000         |1         |400          |1000        |0          |1               |1                 |100            |100         |100            |100           |0       |


Scenario: User is able to scroll in Grid view
Given I navigate to Search
Then I should see Search page
When I enter search criteria (*) on the Search page
When I start search on the Search page
Given I setup Search Authorization

When I open Grid View

Given load story aux-search-stories/entities/auxCheckSearchResulsEntitiesVerticalScroll.story
Given load story aux-search-stories/entities/auxCheckSearchResulsEntitiesHorizontalScroll.story

Given I open Search Filter on the Search page
Given load story ../aux-search-filter-stories/auxSearchFilterShowAllEvents.story
Given I Apply Search using Search Filter on the Search page

Given load story aux-search-stories/events/auxCheckSearchResulsEventsVerticalScroll.story
Given load story aux-search-stories/events/auxCheckSearchResulsEventsHorizontalScroll.story

Examples:
|SIGINT_events|EID_events|GOVINT_events|OSINT_events|CIO_events|Profiler_entities|Documents_entities|SIGINT_entities|EID_entities|GOVINT_entities|OSINT_entities|CIO_entities|
|1000         |1         |400          |1000        |0          |1               |1                 |100            |100         |100            |100           |0       |
