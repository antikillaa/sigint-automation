Meta:
@story reports

Lifecycle:
Before:
Given I sign in as user with permissions FILE_VIEW, FILE_UPDATE, FILE_CREATE
When I send create finder file request
Then Request is successful
Given I sign in as admin user

Scenario: Create a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send delete a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Create a report. [SIGINT] Data Source filters.
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only sourceType from query

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send delete a report request
Then Request is successful

Examples:
| eventFeed | objectType | query  | pageNumber | pageSize |
| SIGINT | event | dataSource:"E" | 0 | 100 |
| SIGINT | event | dataSource:"F" | 0 | 100 |
| SIGINT | event | dataSource:"H" | 0 | 100 |
| SIGINT | event | dataSource:"S" | 0 | 100 |
| SIGINT | event | dataSource:"T" | 0 | 100 |
| SIGINT | entity | dataSource:"E" | 0 | 100 |
| SIGINT | entity | dataSource:"PHONEBOOK" | 0 | 100 |
| SIGINT | entity | dataSource:"Manual" | 0 | 100 |

Scenario: Create a report. [SIGINT] Record Type filters.
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only recordType from query

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send delete a report request
Then Request is successful

Examples:
| eventFeed | objectType | query  | pageNumber | pageSize |
| SIGINT | event | type:"CALL" | 0 | 100 |
| SIGINT | event | type:"LOCATION" | 0 | 100 |
| SIGINT | event | type:"EMAIL" | 0 | 100 |
| SIGINT | event | type:"FAX" | 0 | 100 |
| SIGINT | event | type:"VLR" | 0 | 100 |
| SIGINT | event | type:"MMS_ROAMING" | 0 | 100 |
| SIGINT | event | type:"SMS" | 0 | 100 |
| SIGINT | event | type:"MMS" | 0 | 100 |
| SIGINT | event | type:"VSMS" | 0 | 100 |

Scenario: Submit a report

When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send get owners a report request
Then Request is successful

When I send submit a report request
Then Request is successful
Then Report is submitted

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Take ownership a report

When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send get owners a report request
Then Request is successful

When I send submit a report request
Then Request is successful
Then Report is submitted

When I send take ownership a report request
Then Request is successful
Then Report is took ownership

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Approve a report

When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0
And CB search results contains only eventFeed:<eventFeed> and type:<resultType> records
And CB search result list size < 1001
And pageSize size in response < 1001

When I send send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send get owners a report request
Then Request is successful

When I send submit a report request
Then Request is successful
Then Report is submitted

When I send take ownership a report request
Then Request is successful
Then Report is took ownership

When I send approve a report request
Then Request is successful
Then Report is approved

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |