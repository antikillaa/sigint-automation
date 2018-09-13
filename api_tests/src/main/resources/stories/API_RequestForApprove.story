Meta:
@story rfa

Lifecycle:
Before:
Given I sign in as user with permissions RFI_EXPORT, DESIGNATIONS_READ, RFI_VIEW, TARGETS_INSIGHTS_READ, TARGET_GROUPS_UPDATE, FUNNEL_VIEW, COLLECTION_DELETE, CASE_VIEW, DESIGNATIONS_DELETE, TARGET_GROUPS_VIEW, FILE_UPDATE, DESIGNATIONS_MAP, OPERATOR_REPORT_VIEW, RECORDS_ASSIGN, TARGET_GROUPS_DELETE, RECORD_TAGS_UPDATE, UPLOAD_SOURCE_UPDATE, UM_ADMIN, CASE_CREATE, TARGETS_DELETE, TARGETS_ACTIVATION_UPDATE, RFI_PROCESS_EDIT, SYSTEM_DASHBOARD_VIEW, FILE_DELETE, UM_USER, ACTIVITY_LOG_VIEW, RFI_APPROVE_EDIT, RFA_EXPORT, RFA_UNASSIGN, RECORD_TAGS_READ, RECORDS_CREATE, WHITELIST_UPDATE, DESIGNATIONS_IMPORT, RECORDS_PROCESS, UPLOAD_UPLOAD, COLLECTION_VIEW, RECORDS_TRANSCRIBE, RFA_APPROVE, SEARCH_DELETE, RFI_CREATE, RECORDS_READ, RFA_CREATE, SAVED_SEARCH_VIEW, OPERATOR_REPORT_APPROVE, SEARCH_SCHEDULE, FUNNEL_DELETE, TARGETS_QUERY_HISTORICAL_DATA, TARGET_GROUP_DASHBOARD_VIEW, TARGETS_READ, CASE_DELETE, DATA_PURGE, USER_PRIVILEGE_AUDITING_DASHBOARD_VIEW, REPORT_CATEGORIES_UPDATE, REPORT_CATEGORIES_DELETE, OPERATOR_REPORT_CREATE, CASE_UPDATE, OPERATOR_REPORT_EXPORT, FUNNEL_UPDATE, RFI_UNASSIGN, RECORD_TAGS_DELETE, TARGET_GROUPS_READ, REPORT_CATEGORIES_LIST, TARGETS_UPDATE, VOICE_PRINT_SEARCH, FILE_VIEW, AUDIO_KEYWORD_SEARCH, RFA_VIEW, SEARCH_UPDATE, COLLECTION_UPDATE, DESIGNATIONS_UPDATE, PROFILE_INSIGHTS_DOWNLOAD, FILE_CREATE, TARGETS_VIEW, TARGET_GROUPS_SUBGROUP_UPDATE
When I send create finder file request
Then Request is successful
When I save logged user

Scenario: Create/Delete a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send create a RFA request
Then Request is successful
Then RFA is created

When I send view a RFA request
Then Request is successful

When I send delete a RFA request
Then Request is successful
Then RFA is deleted

When I send view a RFA request
Then Request is unsuccessful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Send for approval a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Cancel sent for approval a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

When I send cancel a RFA request
Then Request is successful
Then RFA is cancelled

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Edit a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send create a RFA request
Then Request is successful
Then RFA is created

When I send view a RFA request
Then Request is successful

When I send update a RFA request
Then Request is successful

When I send delete a RFA request
Then Request is successful
Then RFA is deleted

When I send view a RFA request
Then Request is unsuccessful

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Take ownership a RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Given I sign in as admin user

When I send take ownership a RFA request
Then Request is successful
Then RFA is ownershipped

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Remove Ownership a assigned RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Given I sign in as admin user

When I send take ownership a RFA request
Then Request is successful
Then RFA is ownershipped

When I send remove ownership a RFA request
Then Request is successful
Then RFA is unownershipped

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |


Scenario: Reject a assigned RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Given I sign in as admin user

When I send take ownership a RFA request
Then Request is successful
Then RFA is ownershipped

When I send reject a RFA request
Then Request is successful
Then RFA is rejected

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: Approve a assigned RFA
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Given I sign in as admin user

When I send take ownership a RFA request
Then Request is successful
Then RFA is ownershipped

When I send approve a RFA request
Then Request is successful
Then RFA is approved

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |

Scenario: User able get access to audio after approve RFA
Meta:
@wip
When I send CB search request - query:<query>, eventFeed:<eventFeed>, objectType:<objectType>, pageNumber:<pageNumber>, pageSize:<pageSize>
Then Request is successful
And CB search result list size > 0

When I send generate RFA number request
Then Request is successful

When I send get owner teams a RFA request
Then Request is successful

When I send Send for approval a RFA request
Then Request is successful
Then RFA is created

Given I sign in as admin user

When I send take ownership a RFA request
Then Request is successful
Then RFA is ownershipped

When I send approve a RFA request
Then Request is successful
Then RFA is approved

When I as login first user
Then Request is successful

When I send search for accessed audio request
Then Request is successful
Then Audio content is available
Then User able access to audio

Examples:
| eventFeed | objectType | resultType | query  | pageNumber | pageSize |
| SIGINT | event |  |   type:"CALL" AND eventTime:[$NOW-90d..$NOW] AND senderCountry:"AE" AND receiverCountry:"AE" AND HAS_VPRINT:"true"   | 0 | 1000 |