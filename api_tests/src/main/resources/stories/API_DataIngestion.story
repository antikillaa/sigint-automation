Meta:
@story ingestion


Scenario: API.T-SMS ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And Ingestion directory is clean
And <sourceType> - <recordType> data file with <recordsCount> records was generated
When I send upload data file request
Then Request is successful
When Uploaded file is processed
And Ingest matching is complete
Then Search results contain <recordsCount> records
When I send get upload details request
Then Request is successful
And Matching results contain <recordsCount> records

Examples:
| sourceType | recordType | recordsCount |
| Tactical   | SMS        | 1000         |


Scenario: API.F-SMS ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And Ingestion directory is clean
And <sourceType> - <recordType> data file with <recordsCount> records was generated
When I send upload data file request
Then Request is successful
When Uploaded file is processed
And Ingest matching is complete
Then Search results contain <recordsCount> records
When I send get upload details request
Then Request is successful
And Matching results contain <recordsCount> records

Examples:
| sourceType | recordType | recordsCount |
| F          | SMS        | 1000         |


Scenario: API.S-SMS ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And Ingestion directory is clean
And <sourceType> - <recordType> data file with <recordsCount> records was generated
When I send upload data file request
Then Request is successful
When Uploaded file is processed
And Ingest matching is complete
Then Search results contain <recordsCount> records
When I send get upload details request
Then Request is successful
And Matching results contain <recordsCount> records

Examples:
| sourceType | recordType | recordsCount |
| Strategic  | SMS        | 1000         |