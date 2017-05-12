Meta:
@story ingestion

Scenario: Upload several files with the same name into one source
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful

When I send upload data file request
Then I got response code 409
And Message contains "FileAlreadyExistsException"

Examples:
| sourceType | recordType | recordsCount |
| Tactical   | SMS        | 10           |


Scenario: Upload duplicate data
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful

Given <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then I got response code 400
And Message contains "file is duplicate with etag"

Examples:
| sourceType | recordType | recordsCount |
| Strategic  | SMS        | 10           |


Scenario: API.T-SMS ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
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
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
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
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
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