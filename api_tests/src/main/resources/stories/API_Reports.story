Meta:
@story reports

Lifecycle:
Before:
Given I sign in as admin user
When I send create finder file request
Then Request is successful

Scenario: Create a report
Meta:
@wip
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

Scenario: Delete a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send delete a report request
Then Request is successful

When I send view a report request
Then Request is unsuccessful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 10 |

Scenario: Create a report. [SIGINT] Data Source filters.
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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
| SIGINT | event | dataSource:"DU"| 0 | 100 |
| SIGINT | event | dataSource:"E" | 0 | 100 |
| SIGINT | event | dataSource:"F" | 0 | 100 |
| SIGINT | event | dataSource:"H" | 0 | 100 |
| SIGINT | event | dataSource:"S" | 0 | 100 |
| SIGINT | event | dataSource:"T" | 0 | 100 |
| SIGINT | entity | dataSource:"DU" | 0 | 100 |
| SIGINT | entity | dataSource:"E" | 0 | 100 |
| SIGINT | entity | dataSource:"PHONEBOOK" | 0 | 100 |
| SIGINT | event | dataSource:"O"| 0 | 100 |

Scenario: Create a report. [SIGINT] J1, J2 sources.
Meta:
@dev
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send view a report request
Then Request is successful

When I send delete a report request
Then Request is successful

Examples:
| SIGINT | event | dataSource:"J1"| 0 | 100 |
| SIGINT | event | dataSource:"J2" | 0 | 100 |

Scenario: Create a report. [SIGINT] Data Subsource filters
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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
| SIGINT | event | subSource:"CDR" | 0 | 100 |
| SIGINT | event | subSource:"CELL" | 0 | 100 |
| SIGINT | event | subSource:"FAX" | 0 | 100 |
| SIGINT | event | subSource:"SMS" | 0 | 100 ||
| SIGINT | event | subSource:"VLR" | 0 | 100 |
| SIGINT | event | subSource:"Voice" | 0 | 100 |
| SIGINT | event | subSource:"MMS" | 0 | 100 |
| SIGINT | event | subSource:"NLD" | 0 | 100 |
| SIGINT | entity | subSource:"Subscriber" | 0 | 100 |

Scenario: Create a report. [SIGINT] Record Type filters.
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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
| SIGINT | event | type:"FAX" | 0 | 100 |
| SIGINT | event | type:"VLR" | 0 | 100 |
| SIGINT | event | type:"MMS_ROAMING" | 0 | 100 |
| SIGINT | event | type:"SMS" | 0 | 100 |
| SIGINT | event | type:"MMS" | 0 | 100 |
| SIGINT | event | type:"VSMS" | 0 | 100 |
| SIGINT | event | type:"SIP_VIDEO" | 0 | 100 |
| SIGINT | entity | type:"TELECOM_SUBSCRIBER" | 0 | 100 |

Scenario: Submit a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

When I send generate report number request
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

When I send generate report number request
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

Scenario: Return to author a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

When I send return to author a report request
Then Request is successful
Then Report is returned to author

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Reject a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

When I send reject a report request
Then Request is successful
Then Report is rejected

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Cancel a report with owner
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

When I send cancel a report request with owner
Then Request is successful
Then Report is canceled

When I send view a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Cancel a report without owner
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
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

When I send cancel a report request without owner
Then Request is successful
Then Report is canceled

When I send view a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 150 |
| SIGINT | entity| EntityVO|      | 0 | 150 |

Scenario: Update a report
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate report number request
Then Request is successful

When I send create a report request
Then Request is successful
And Report is created

When I send edit a report request
Then Request is successful

When I send get owners a report request
Then Request is successful

When I send submit a report request
Then Request is successful
Then Report is submitted

When I send edit a report request
Then Request is successful

When I send get owners a report request
Then Request is successful

When I send submit a report request
Then Request is successful
Then Report is submitted

When I send take ownership a report request
Then Request is successful
Then Report is took ownership

When I send edit a report request
Then Request is successful

When I send delete a report request
Then Request is successful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event | EventVO |      | 0 | 10 |