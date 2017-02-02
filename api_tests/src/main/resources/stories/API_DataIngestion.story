Meta:
@component ingestion


Scenario: API.Generate SSMS
Meta:
@deprecated
Given I sign in as admin user
And Generate 3 SSMS


Scenario: API.S-SMS ingestion. Target matching by phone number, name and keywords.
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And <targetCount> targets with phones generated and added
And <sourceType> - <recordType> data file with records for test targets was generated
When I send upload <sourceType> - <recordType> data file request
Then Request is successful
When uploaded file is processed
And ingest matching complete
Then search results contain correct counts: total records
When I send get upload details request
Then Request is successful
And matching results contain correct Summary: total records
And all uploaded records ingested to system with targets Hits & Mentions

Examples:
| sourceType | recordType | targetCount |
| Strategic  | SMS        | 2           |


Scenario: API.F-SMS ingestion. Target matching by phone number, name and keywords.
Given I sign in as admin user
And data source with <sourceType> and <recordType> exists
And <targetCount> targets with phones generated and added
And <sourceType> - <recordType> data file with records for test targets was generated
When I send upload <sourceType> - <recordType> data file request
Then Request is successful
When uploaded file is processed
And ingest matching complete
Then search results contain correct counts: total records
When I send get upload details request
Then Request is successful
And matching results contain correct Summary: total records
And all uploaded records ingested to system with targets Hits & Mentions

Examples:
| sourceType | recordType | targetCount |
| F          | SMS        | 2           |
