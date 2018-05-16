TODO list:
GOVINT - Fuzzy, Wildcard
PROFILER - Fuzzy, Wildcard
entity - add Wildcard, Fuzzy scenarios

Meta:
@story search

Lifecycle:
Before:
Given I sign in as admin user

Scenario: SIGINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 1100 |
| SIGINT | entity | EntityVO |     | 0 | 1100 |


Scenario: OSINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| OSINT  | event | EventVO |      | 0 | 1100 |
| OSINT  | entity | EntityVO |     | 0 | 1100 |


Scenario: GOVINT. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| GOVINT | event | EventVO |      | 0 | 1100 |
| GOVINT | entity | EntityVO |     | 0 | 1100 |


Scenario: PROFILER. The maximum number of search results for a single query is limited to 1000 records
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| PROFILER | entity | TargetVO |   | 0 | 1100 |


Scenario: SIGINT. Wild Card Search
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
Then CB search contains results for query

Examples:
| eventFeed | objectType | query | pageNumber | pageSize |
| SIGINT | event | knowledge | 0 | 20 |
| SIGINT | event | kno?le?ge | 0 | 20 |
| SIGINT | event | k*ledge   | 0 | 20 |
| SIGINT | event | k*le?ge  | 0 | 20 |


Scenario: OSINT. Wild Card Search
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
Then CB search contains results for query

Examples:
| eventFeed | objectType | query | pageNumber | pageSize |
| OSINT | event | knowledge | 0 | 20 |
| OSINT | event | kno?le?ge | 0 | 20 |
| OSINT | event | k*ledge   | 0 | 20 |
| OSINT | event | k*le?ge  | 0 | 20 |


Scenario: SIGINT. Fuzzy Search
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
Then CB search contains results for query

Examples:
| eventFeed | objectType | query | pageNumber | pageSize |
| SIGINT | event | knoeledge~1 | 0 | 20 |
| SIGINT | event | knowledge~2  | 0 | 20 |
| SIGINT | event | knwldg~3 | 0 | 20 |
| SIGINT | event | nowledge~  | 0 | 20 |


Scenario: OSINT. Fuzzy Search
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
Then CB search contains results for query

Examples:
| eventFeed | objectType | query | pageNumber | pageSize |
| OSINT | event | knoeledge~1 | 0 | 20 |
| OSINT | event | knowledge~2  | 0 | 20 |
| OSINT | event | knwldg~3 | 0 | 20 |
| OSINT | event | nowledge~  | 0 | 20 |


Scenario: Search. [SIGINT] Workflow filters. Record status: Unassigned
When I send workflow search request: record status:<recordStatus>, source:<source>, objectType:<objectType>,
pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results match the recordStatus filters

Examples:
| source | objectType | recordStatus | pageNumber | pageSize |
| SIGINT | event | unassigned | 0 | 300 |


Scenario: Search. [SIGINT] Workflow filters. Record status: Unprocessed
When I send workflow search request: record status:<recordStatus>, source:<source>, objectType:<objectType>,
pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results match the recordStatus filters

Examples:
| source | objectType | recordStatus | pageNumber | pageSize |
| SIGINT | event | unprocessed | 0 | 300 |


Scenario: Search. [SIGINT] Workflow filters. Record status: Reported
When I send workflow search request: record status:<recordStatus>, source:<source>, objectType:<objectType>,
pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results match the recordStatus filters

Examples:
| source | objectType | recordStatus | pageNumber | pageSize |
| SIGINT | event | reported | 0 | 300 |


Scenario: Search. [SIGINT] Workflow filters. Record status: Unimportant
When I send workflow search request: record status:<recordStatus>, source:<source>, objectType:<objectType>,
pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results match the recordStatus filters

Examples:
| source | objectType | recordStatus | pageNumber | pageSize |
| SIGINT | event | unimportant | 0 | 300 |


Scenario: Search. [SIGINT] Data Source filters.
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only sourceType from query

Examples:
| eventFeed | objectType | query  | pageNumber | pageSize |
| SIGINT | event | dataSource:"E" | 0 | 300 |
| SIGINT | event | dataSource:"F" | 0 | 300 |
| SIGINT | event | dataSource:"H" | 0 | 300 |
| SIGINT | event | dataSource:"S" | 0 | 300 |
| SIGINT | event | dataSource:"T" | 0 | 300 |
| SIGINT | entity | dataSource:"H" | 0 | 300 |
| SIGINT | entity | dataSource:"E" | 0 | 300 |
| SIGINT | entity | dataSource:"PHONEBOOK" | 0 | 300 |


Scenario: Search. [SIGINT] Data Subsource filters
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only subSource from query

Examples:
| eventFeed | objectType | query  | pageNumber | pageSize |
| SIGINT | event | subSource:"CDR" | 0 | 300 |
| SIGINT | entity | subSource:"CDR" | 0 | 300 |
| SIGINT | event | subSource:"CELL" | 0 | 300 |
| SIGINT | entity | subSource:"CELL" | 0 | 300 |
| SIGINT | event | subSource:"Fax" | 0 | 300 |
| SIGINT | entity | subSource:"Fax" | 0 | 300 |
| SIGINT | event | subSource:"SMS" | 0 | 300 |
| SIGINT | entity | subSource:"SMS" | 0 | 300 |
| SIGINT | event | subSource:"VLR" | 0 | 300 |
| SIGINT | entity | subSource:"VLR" | 0 | 300 |
| SIGINT | event | subSource:"Voice" | 0 | 300 |
| SIGINT | entity | subSource:"Voice" | 0 | 300 |


Scenario: Search. [SIGINT] Record Type filters
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only recordType from query

Examples:
| eventFeed | objectType | query  | pageNumber | pageSize |
| SIGINT | event | type:"CALL" | 0 | 300 |
| SIGINT | event | type:"LOCATION" | 0 | 300 |
| SIGINT | event | type:"EMAIL" | 0 | 300 |
| SIGINT | event | type:"FAX" | 0 | 300 |
| SIGINT | event | type:"VLR" | 0 | 300 |
| SIGINT | event | type:"MMS_ROAMING" | 0 | 300 |
| SIGINT | event | type:"SMS" | 0 | 300 |
| SIGINT | event | type:"MMS" | 0 | 300 |
| SIGINT | event | type:"VSMS" | 0 | 300 |
| SIGINT | event | type:"SIP_VIDEO" | 0 | 300 |
| SIGINT | entity | type:"EMAIL_ACCOUNT" | 0 | 300 |
| SIGINT | entity | type:"PHONE" | 0 | 300 |
| SIGINT | entity | type:"TELECOM_SUBSCRIBER" | 0 | 300 |


Scenario: Search. [SIGINT] EventTime filter
When I send CB search request - eventTime:<eventTime>, source:<source>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventTime from query

Examples:
| source | objectType | eventTime | pageNumber | pageSize |
| SIGINT | event | LAST_MONTH | 0 | 300 |


Scenario: POST /api/search/count
When I send CB search count request - query:<query>, objectType:<objectType>, sources:<source>
Then Request is successful
And CB search result list size > 0
And TotalCount's in search results <criteria> <size>

Examples:
| source                            | objectType| query | criteria  | size  |
| SIGINT, OSINT, GOVINT             | event     |       | >         | 0     |
| SIGINT, OSINT, GOVINT, PROFILER   | entity    |       | >         | 0     |