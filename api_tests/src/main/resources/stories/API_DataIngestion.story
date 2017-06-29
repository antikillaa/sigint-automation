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
| S          | SMS        | 10           |


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
| F          | SMS        | 10           |


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
| S          | SMS        | 10           |


Scenario: API.T-SMS ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and T exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| T          | SMS        | 1000         |


Scenario: API.F-SMS ingestion
Meta: @skip
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| F          | SMS        | 1000         |


Scenario: API.Phonebook ingestion
Meta: @skip
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| Phonebook  | Phonebook  | 1000         |


Scenario: API.ETISALAT-Subscriber ingestion
Meta: @skip
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| E          | Subscriber | 1000         |


Scenario: API.ETISALAT-CDR ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| ETISALAT   | CDR        | 1000         |


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

Examples:
| sourceType | recordType | recordsCount |
| S          | SMS        | 1000         |


Scenario: API.S-VLR ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | VLR        | 1000         |


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

Examples:
| sourceType | recordType | recordsCount |
| S          | CDR        | 100          |


Scenario: API.S-CELL ingestion
Meta: @skip
Given I sign in as admin user
And Data source with <sourceType> and <recordType> exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I send upload data file request
Then Request is successful
And Uploaded file is processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| S          | CELL       | 100          |


Scenario: API.T-Voice ingestion
Meta: @nightly
Given I sign in as admin user
And Data source with <sourceType> and T exists
And <sourceType> - <recordType> data file with <recordsCount> records was generated
And <sourceType> - <recordType> data file is renamed to make filename unique
When I upload audio files
When I send upload data file request
Then Request is successful
Then Uploaded file is processed
Then Uploaded audio files are processed
And Original data file is searchable within the system

Examples:
| sourceType | recordType | recordsCount |
| T          | Voice      | 10           |


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
| T          | T          |
| F          | SMS        |
| S          | SMS        |


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
| T          | T          |
| F          | SMS        |
| S          | SMS        |


Scenario: API.Search and count data files by wildcard
Meta: @nightly
Given I sign in as admin user
When I search data files with date pattern "*/yyyy/MM/dd/*"
Then Found files list size is more than 0
When I send count found files request
Then Files count result is equal to appropriate list size