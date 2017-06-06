Meta:
@story ingestion

Scenario: API.Ingestion with the wrong source type
Meta: @nightly
Given I sign in as admin user
And Data source with F and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Message contains "file with id"

Examples:
| sourceType | recordType | recordsCount |
| Strategic  | SMS        | 10           |
| Tactical   | SMS        | 10           |


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
And Uploaded file is processed
And Original data file is searchable within the system
And <recordsCount> records are ingested
When I send get upload details request
Then Request is successful
And Upload details contain <recordsCount> - Total records
And Upload details contain <recordsCount> - <recordType> records

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
And Uploaded file is processed
And Original data file is searchable within the system
And <recordsCount> records are ingested
When I send get upload details request
Then Request is successful
And Upload details contain <recordsCount> - Total records
And Upload details contain <recordsCount> - <recordType> records

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
And Uploaded file is processed
And Original data file is searchable within the system
And <recordsCount> records are ingested
When I send get upload details request
Then Request is successful
And Upload details contain <recordsCount> - Total records
And Upload details contain <recordsCount> - <recordType> records

Examples:
| sourceType | recordType | recordsCount |
| Strategic  | SMS        | 1000         |


Scenario: API.S-CDR ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system
And <recordsCount> records are ingested
When I send get upload details request
Then Request is successful
And Upload details contain <recordsCount> - Total records
And Upload details contain <recordsCount> - <recordType> records

Examples:
| sourceType | recordType | recordsCount |
| Strategic  | Voice      | 100          |


Scenario: API.T-Voice ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
When I upload audio files
Then Uploaded file is processed
And Original data file is searchable within the system
And <recordsCount> records are ingested
When I send get upload details request
Then Request is successful
And Upload details contain <recordsCount> - Total records
And Upload details contain <recordsCount> - <recordType> records

Examples:
| sourceType | recordType | recordsCount |
| Tactical   | Voice      | 10           |


Scenario: API.Search and count data folders by path
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
When I search data folders by source with date pattern "yyyy/MM"
Then Found folders list size is more than 0
When I send count found folders request
Then Folders count result is equal to appropriate list size

Examples:
| sourceType | recordType |
| Tactical   | Voice      |
| F          | SMS        |
| Strategic  | SMS        |


Scenario: API.Search and count data folders by wildcard
Meta: @nightly
Given I sign in as admin user
When I search data folders with date pattern "*/yyyy"
Then Found folders list size is more than 0
When I send count found folders request
Then Folders count result is equal to appropriate list size


Scenario: API.Search and count data files by path
Meta: @nightly

Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
When I search data folders by source with date pattern "yyyy/MM/dd"
Then Found folders list size is more than 0
When I search data files by path
Then Found files list size is more than 0
When I send count found files request
Then Files count result is equal to appropriate list size

Examples:
| sourceType | recordType |
| Tactical   | Voice      |
| F          | SMS        |
| Strategic  | SMS        |


Scenario: API.Search and count data files by wildcard
Meta: @nightly
Given I sign in as admin user
When I search data files with date pattern "*/yyyy/MM/dd/*"
Then Found files list size is more than 0
When I send count found files request
Then Files count result is equal to appropriate list size